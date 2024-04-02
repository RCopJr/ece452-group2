package com.example.groupgains.login

import android.app.Activity
import android.app.AlertDialog
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
            "friends" to emptyList<String>()
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

    fun onRegister(email: String, password: String, context: Activity) {
        val builder = AlertDialog.Builder(context)

        builder.setMessage("Before you proceed, please take a moment to review the following important health and safety information:\n" +
                "\n" +
                "Proper Form: It is crucial to maintain proper weightlifting form to prevent injuries. Always ensure that you are using correct technique for each exercise. If you are unsure about proper form, seek guidance from a certified fitness trainer.\n" +
                "\n" +
                "Know Your Limits: Lift weights within your capabilities. Avoid lifting weights that are excessively heavy for your current strength level. Do not compare your abilities to those of an athlete or professional. Gradually increase the weight as you progress to prevent overexertion and potential injury.\n" +
                "\n" +
                "Listen to Your Body: Pay attention to any discomfort or pain during your workouts. Stop immediately if you experience any sharp pain or unusual discomfort. Pushing through pain can lead to serious injuries.\n" +
                "\n" +
                "Consult Your Physician: If you have any pre-existing medical conditions or concerns about your ability to engage in weightlifting exercises, consult with your physician before starting any new fitness regimen.\n" +
                "\n" +
                "By proceeding to use this app, you acknowledge and agree to adhere to these health and safety guidelines. \n" +
                "\n" +
                "Enjoy!\n" +
                "\n" +
                "[Disclaimer: This app is intended for informational purposes only and friend’s regimens should not be considered a substitute for professional medical advice. Always consult with a qualified healthcare provider before starting or changing a fitness routine.]")

        builder.setTitle("Welcome to GroupGains!")

        // Set Cancelable false for when the user clicks on the outside the Dialog Box then it will remain show
        builder.setCancelable(false)

        // Set the positive button with yes name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setPositiveButton("I understand- Register me!") {
            // When the user click yes button then app will close
                dialog, which -> dialog.cancel()
                createUser(email, password, context)
        }

        // Set the Negative button with No name Lambda OnClickListener method is use of DialogInterface interface.
        builder.setNegativeButton("Cancel") {
            // If user click no then dialog box is canceled.
                dialog, which -> dialog.cancel()
        }

        // Create the Alert dialog
        val alertDialog = builder.create()
        // Show the Alert Dialog box
        alertDialog.show()
    }
}