package com.example.groupgains.ui.profile

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupgains.data.Exercise
import com.example.groupgains.data.Set
import com.example.groupgains.data.Workout
import com.example.groupgains.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.example.groupgains.data.User

class ProfileViewModel : ViewModel() {

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    val workoutsLiveData: MutableLiveData<List<Workout>> = MutableLiveData()
    
    val user = MutableLiveData<User>()


    fun initializeActivity(context: Activity){
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            goToLogin(context)
        }
        val userId = auth.currentUser?.uid
        if (userId != null) {
            loadWorkoutData(userId)
            Log.d("TEST IN VIEW MODAL", "TEST")
        }
        loadUserData(auth.currentUser!!.uid)
    }

    fun loadUserData(userID: String){
        // user_id.postValue(userID)
        val userRef = db.collection("users")
        userRef.whereEqualTo("user_id", userID)
        .get()
        .addOnSuccessListener { documents ->
            val userDocument = documents.documents.firstOrNull()
            if (userDocument != null) {
                Log.d("Load User", "${userDocument.id} => ${userDocument.data}")
                val userData = userDocument.toObject(User::class.java)
                user.postValue(userData)
            } else {
                Log.d("Load User", "No document found for userID: $userID")
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Load User", "Error getting documents: ", exception)
        }
    }

    public fun updateNameInDb(userName: String) {
        println("about to update username in db ${userName}")
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("users")
            .whereEqualTo("user_id", userId)
            .get()
            .addOnSuccessListener {
                documents ->
                val userDocument = documents.documents.firstOrNull()

                val id = userDocument!!.id;
                db.collection("users").document(id).update(mapOf(
                    "userName" to userName
                ))
                // Document updated successfully
                // You can handle success here if needed
                println("update success")
            }
            .addOnFailureListener { e ->
                // Error handling
                println("Error updating username: $e")
            }
        }
    }

    fun signOut(context: Activity){
        auth.signOut()
        goToLogin(context)
    }

    fun loadWorkoutData(userID: String){
        val workoutsCollection = db.collection("workouts")
        workoutsCollection.whereEqualTo("user_id", userID)
            .get()
            .addOnSuccessListener { documents ->
                val workoutsList = mutableListOf<Workout>()
                for (document in documents) {
                    // Handle each document (workout) here
                    val workout = document.toObject<Workout>()
                    for (exercise in workout.exercises) {
                        exercise.numSets = exercise.sets.count()
                    }
                    Log.d("WORKOUT DATA", "$workout")
                    workoutsList.add(workout)
                }
                workoutsLiveData.value = workoutsList
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occurred
                Log.e("Firestore", "Error getting workouts", exception)
            }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is the profile Fragment"
    }
    val text: LiveData<String> = _text

    private val mutableSelectedWorkout = MutableLiveData<Workout>()
    val selectedWorkout: LiveData<Workout> get() = mutableSelectedWorkout

    fun selectWorkout(workout: Workout) {
        mutableSelectedWorkout.value = workout
    }

    private fun goToLogin(context: Activity) {
        context.startActivity(Intent(context, LoginActivity::class.java))
        context.finish()
    }

}