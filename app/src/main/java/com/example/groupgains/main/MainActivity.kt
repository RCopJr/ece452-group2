package com.example.groupgains.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import android.util.Log
import androidx.activity.viewModels
import com.example.groupgains.R
import com.example.groupgains.databinding.ActivityMainBinding
import com.example.groupgains.home.HomeActivity
import com.example.groupgains.home.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ActivityContext
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: AppCompatActivity() {

//    @ActivityContext private lateinit var activity: Context

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initializeActivity(this)

        findViewById<MaterialButton>(R.id.btLogin).setOnClickListener {

            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@MainActivity, "Email or password cannot be empty for login.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.authenticateUser(email, password, this)
            }
        }


        // register user
        findViewById<MaterialButton>(R.id.btRegister).setOnClickListener {

            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@MainActivity, "Email or password cannot be empty for registration.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.createUser(email, password, this)
            }
        }

    }
}