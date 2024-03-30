package com.example.groupgains.ui.create

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.groupgains.data.Exercise
import com.example.groupgains.data.Set
import com.example.groupgains.data.Workout
import com.example.groupgains.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import javax.inject.Inject

//class CreateViewModelFactory() : ViewModelProvider.Factory {
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(CreateViewModel::class.java)) {
//            @Suppress("UNCHECKED_CAST")
//            return CreateViewModel() as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}

class CreateViewModel @Inject constructor(): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is create Fragment"
    }

    val workoutIDLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val _workoutLiveData = MutableLiveData<Workout?>()

    val workoutLiveData: LiveData<Workout?> = _workoutLiveData

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
                    workout.id = document.id
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

    fun updateWorkoutID(newValue: String) {
        workoutIDLiveData.value = newValue
    }

    fun updateWorkoutData(newWorkout: Workout) {
        _workoutLiveData.value = newWorkout
    }

    fun addExercise() {
        val currentWorkoutData = workoutLiveData.value
        currentWorkoutData?.exercises?.add(Exercise())

        Log.d("CURRENT WORKOUT DATA", "$currentWorkoutData")
        if (currentWorkoutData != null) {
            updateWorkoutData(currentWorkoutData)
        }
    }

    fun addSet(exerciseToEdit: Exercise) {
        val currentWorkoutData = workoutLiveData.value
        for (exercise in currentWorkoutData!!.exercises) {
            if (exercise == exerciseToEdit) {
                exercise.sets.add(Set())
            }
        }

        updateWorkoutData(currentWorkoutData)
    }

    fun editWorkoutTitle(newTitle: String) {
        val currentWorkoutData = workoutLiveData.value
        if (currentWorkoutData != null) {
            currentWorkoutData.title = newTitle
        }
    }

    fun editExerciseTitle(newTitle: String, exerciseToEdit: Exercise) {
        val currentWorkoutData = workoutLiveData.value
        for (exercise in currentWorkoutData!!.exercises) {
            if (exercise == exerciseToEdit) {
                exercise.title = newTitle
            }
        }
    }

    fun editSetTitle(newTitle: String, setToEdit: Set) {
        val currentWorkoutData = workoutLiveData.value
        for (exercise in currentWorkoutData!!.exercises) {
            for (set in exercise.sets) {
                if (set == setToEdit) {
                    set.title = newTitle
                }
            }
        }
    }

    fun saveWorkout() {
        val currentWorkoutLiveData = workoutLiveData.value
        for (exercise in currentWorkoutLiveData?.exercises!!) {
            if (exercise.title == "") {
                currentWorkoutLiveData.exercises.remove(exercise)
            } else {
                for (set in exercise.sets) {
                    if (set.title == "") {
                        exercise.sets.remove(set)
                    }
                }
            }
        }

        if (workoutIDLiveData.value == "") {
            currentWorkoutLiveData.user_id = auth.currentUser!!.uid
            addWorkoutToDb(currentWorkoutLiveData)
        } else {
            currentWorkoutLiveData.user_id = auth.currentUser!!.uid
            updateWorkoutInDb(currentWorkoutLiveData)
        }
    }

    fun deleteExercise(exerciseToDelete: Exercise) {
        val currentWorkoutLiveData = workoutLiveData.value
        val iterator = currentWorkoutLiveData!!.exercises.iterator()

        while(iterator.hasNext()){
            val exercise = iterator.next()
            if (exercise == exerciseToDelete) {
                iterator.remove()
            }
        }

        updateWorkoutData(currentWorkoutLiveData)
    }

    private fun updateWorkoutInDb(existingWorkout: Workout) {
        db.collection("workouts")
            .document(existingWorkout.id)
            .update(existingWorkout.toMap())
            .addOnSuccessListener {
                // Document updated successfully
                // You can handle success here if needed
            }
            .addOnFailureListener { e ->
                // Error handling
                println("Error adding workout: $e")
            }
    }

    private fun addWorkoutToDb(newWorkout: Workout) {
        val workoutsCollection = db.collection("workouts")
        workoutsCollection
            .add(newWorkout)
            .addOnSuccessListener { documentReference ->
                // The workout data was added successfully
                println("Workout added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                // Handle any errors that occurred while adding the workout data
                println("Error adding workout: $e")
            }
    }
    private fun goToLogin(context: Activity) {
        context.startActivity(Intent(context, LoginActivity::class.java))
        context.finish()
    }
}