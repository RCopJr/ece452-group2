package com.example.groupgains.login

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.groupgains.home.HomeActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(): ViewModel() {

    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore
    fun initializeActivity(context: Activity){

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null) {
            Toast.makeText(context, "User is already logged in", Toast.LENGTH_SHORT).show()
            goToHome(context)
        }
    }

    private fun goToHome(context: Activity){
        context.startActivity(Intent(context, HomeActivity::class.java))
        context.finish()
    }

    fun authenticateUser(email: String, password: String, context: Activity){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
                    goToHome(context)
                } else {
                    Toast.makeText(context, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun createUser(email: String, password: String, context: Activity){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener {
                val userId = it.result?.user?.uid
                if (it.isSuccessful) {
                    Toast.makeText(context, "User successfully created with ID: $userId", Toast.LENGTH_SHORT).show()
                    Toast.makeText(context, "User successfully created", Toast.LENGTH_SHORT).show()
                    if (userId != null) {
                        addUserToCollection(userId, email)
                    }
                    goToHome(context)
                } else {
                    Toast.makeText(context, "Authentication of new user failed", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun addUserToCollection(userID: String, email: String) {
        val userRef = db.collection("users")

        // Create a new user with a first and last name, and the user ID
        val newUser = hashMapOf(
            "userName" to email,
            "user_id" to userID,
            "friends" to emptyList<String>(),
            "friendRequest" to emptyList<String>()
        )

        // Add a new document with an auto-generated ID
        userRef.add(newUser)
            .addOnSuccessListener { documentReference ->
                Log.d("RegisterUser", "User successfully written! ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("RegisterUser", "Error writing user", e)
            }

    }
}