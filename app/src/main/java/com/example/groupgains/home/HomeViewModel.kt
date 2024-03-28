package com.example.groupgains.home

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.groupgains.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {


    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    fun initializeActivity(context: Activity){
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            goToLogin(context)
        }
        loadUserData(auth.currentUser!!.uid)
    }
    fun loadUserData(userID: String){
        val userRef = db.collection("users")
        userRef.whereEqualTo("user_id", userID)
            .get()
            .addOnSuccessListener { documents ->
                val userDocument = documents.documents.firstOrNull()
                if (userDocument != null) {
//                    user_data = userDocument.data!!
                    Log.d("Load User", "${userDocument.id} => ${userDocument.data}")
                } else {
//                    user_data = null
                    Log.d("Load User", "No document found for userID: $userID")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Load User", "Error getting documents: ", exception)
            }
    }

    fun signOut(context: Activity){
        auth.signOut()
        goToLogin(context)
    }

    fun loadWorkoutData(workoutID: String, context: Activity) {

        val workoutsRef = db.collection("workouts")
        workoutsRef.document(workoutID)
            .get()
            .addOnSuccessListener {
                if (!it.exists()) {
                    Toast.makeText(context, "No Workout Found", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

            }
    }

    private fun goToLogin(context: Activity) {
        context.startActivity(Intent(context, MainActivity::class.java))
        context.finish()
    }

}