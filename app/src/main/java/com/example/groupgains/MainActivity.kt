package com.example.groupgains

import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.groupgains.databinding.ActivityMainBinding
import com.example.groupgains.ui.home.HomeFragment
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            Toast.makeText(this@MainActivity, "User is already logged in", Toast.LENGTH_SHORT).show()
            goToHome()
        }
        
        findViewById<MaterialButton>(R.id.btLogin).setOnClickListener {

            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@MainActivity, "Email or password cannot be empty for login.", Toast.LENGTH_SHORT).show()
            } else {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if (it.isSuccessful) {
                            Toast.makeText(this@MainActivity, "Success", Toast.LENGTH_SHORT).show()
                            goToHome()
                        } else {
                            Toast.makeText(this@MainActivity, "Authentication Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }


        // register user
        findViewById<MaterialButton>(R.id.btRegister).setOnClickListener {

            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@MainActivity, "Email or password cannot be empty for registration.", Toast.LENGTH_SHORT).show()
            } else {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {

                        if (it.isSuccessful) {
                            Toast.makeText(this@MainActivity, "User successfully created", Toast.LENGTH_SHORT).show()
                            goToHome()
                        } else {
                            Toast.makeText(this@MainActivity, "Authentication of new user failed", Toast.LENGTH_SHORT).show()
                        }
                    }
            }
        }

    }

    private fun goToHome() {
        startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        finish()
    }
}