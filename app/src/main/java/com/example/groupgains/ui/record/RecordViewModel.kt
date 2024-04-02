package com.example.groupgains.ui.record

import android.app.Activity
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupgains.data.Exercise
import com.example.groupgains.data.Reactions
import com.example.groupgains.data.Session
import com.example.groupgains.data.Set
import com.example.groupgains.data.Stats
import com.example.groupgains.data.Workout
import com.example.groupgains.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class RecordViewModel : ViewModel() {

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    val workoutsLiveData: MutableLiveData<List<Workout>> = MutableLiveData()

    private val mutableSelectedWorkout = MutableLiveData<Workout?>()
    val selectedWorkout: MutableLiveData<Workout?> get() = mutableSelectedWorkout

    private val mutableTotalSets = MutableLiveData<String>()
    val totalSets: LiveData<String> get() = mutableTotalSets

    private val mutableCompletion = MutableLiveData<String>()
    val completion: LiveData<String> get() = mutableCompletion

    private val mutableVolume = MutableLiveData<String>()
    val volume: LiveData<String> get() = mutableVolume

    private val mutableTotalTime = MutableLiveData<String>()
    val totalTime: LiveData<String> get() = mutableTotalTime

    private val mutableFeedback = MutableLiveData<String>("3")
    val feedback: LiveData<String> get() = mutableFeedback

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
                        for (set in exercise.sets) {
                            set.checked = false
                        }
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

    fun selectWorkout(workout: Workout) {
        mutableSelectedWorkout.value = workout
    }

    fun handleSetChecked(finishedSet: Set, checkedValue: Boolean) {
        val currentWorkoutData = mutableSelectedWorkout.value
        for (exercise in currentWorkoutData!!.exercises) {
            for (set in exercise.sets) {
                if (set == finishedSet) {
                    set.checked = checkedValue
                    Log.d("TEST THAT THIS SET WAS CHECKED", "${set}, ${set.checked}")
                }
            }
        }
    }

    fun handleUpdateFeedback(newFeedback: String) {
        mutableFeedback.value = newFeedback
    }

    fun createSession(totalTime: String) {
        val currentWorkoutData = mutableSelectedWorkout.value
        mutableTotalTime.value = totalTime
        var totalVolume = 0
        var completedSets = 0
        var allSets = 0
        for (exercise in currentWorkoutData!!.exercises) {
            for (set in exercise.sets) {
                if (set.checked) {
                    completedSets += 1
                    totalVolume += set.reps.toInt() * set.weight.toInt()
                }
                allSets += 1
            }
        }
        mutableCompletion.value = ((completedSets.toDouble() / allSets) * 100).toInt().toString()
        mutableTotalSets.value = completedSets.toString()
        mutableVolume.value = totalVolume.toString()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getCurrentDateTime(): String {
        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        return currentDateTime.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun saveSessionToDb() {

        val newSession = Session(
            user_id =  auth.currentUser!!.uid,
            id = "",
            workoutId = selectedWorkout.value!!.id,
            timestamp = getCurrentDateTime(),
            stats = Stats(totalSets.value, volume.value, feedback.value, totalTime.value, completion.value),
            reactions = Reactions()
        )

        val sessionCollection = db.collection("sessions")
        sessionCollection
            .add(newSession)
            .addOnSuccessListener { documentReference ->
                println("Workout added with ID: ${documentReference.id}")
                db.collection("sessions")
                    .document(documentReference.id)
                    .update(mapOf("id" to documentReference.id))
                    .addOnSuccessListener {
                    }
                    .addOnFailureListener { e ->
                        println("Error adding workout: $e")
                    }
            }
            .addOnFailureListener { e ->
                println("Error adding workout: $e")
            }
    }
    private fun goToLogin(context: Activity) {
        context.startActivity(Intent(context, LoginActivity::class.java))
        context.finish()
    }

}