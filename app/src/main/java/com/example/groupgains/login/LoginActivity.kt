package com.example.groupgains.login

import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import androidx.activity.viewModels
import com.example.groupgains.R
import com.example.groupgains.databinding.ActivityLoginBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity: AppCompatActivity() {

//    @ActivityContext private lateinit var activity: Context

    private lateinit var binding: ActivityLoginBinding

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initializeActivity(this)

        findViewById<MaterialButton>(R.id.btLogin).setOnClickListener {

            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Email or password cannot be empty for login.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.authenticateUser(email, password, this)
            }
        }


        // register user
        findViewById<MaterialButton>(R.id.btRegister).setOnClickListener {

            val email = findViewById<EditText>(R.id.etEmail).text.toString().trim()
            val password = findViewById<EditText>(R.id.etPassword).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this@LoginActivity, "Email or password cannot be empty for registration.", Toast.LENGTH_SHORT).show()
            } else {
                viewModel.createUser(email, password, this)
            }
        }

    }
}