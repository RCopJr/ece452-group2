package com.example.groupgains.ui.record

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

class RecordViewModel : ViewModel() {

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    val workoutsLiveData: MutableLiveData<List<Workout>> = MutableLiveData()

    fun initializeActivity(context: Activity){
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            goToLogin(context)
        }
        loadWorkoutData(auth.currentUser!!.uid)
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
        value = "This is record Fragment"
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