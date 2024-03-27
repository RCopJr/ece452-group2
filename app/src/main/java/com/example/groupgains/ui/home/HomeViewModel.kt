package com.example.groupgains.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupgains.ui.record.Exercise
import com.example.groupgains.ui.record.Set
import com.example.groupgains.ui.record.Workout
import com.example.groupgains.ui.record.Workouts
import com.google.firebase.Firebase
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.firestore

data class Parameters(val userId: String = "")

class GetWorkoutsOfUser {
    fun loadWorkouts(parameters: Parameters, onLoad: (Workouts) -> Unit) {
        val db = Firebase.firestore;
        db.collection("users")
            .document("user1")
            .get()
            .addOnSuccessListener { user ->
                val userData = user.data
                if (userData != null) {
                    db.collection("workouts")
                        .whereIn(FieldPath.documentId(), listOf(userData["workouts"]))
                        .get()
                        .addOnSuccessListener { workouts ->
                            for (document in workouts) {
                                Log.d("User GET TEST", "${document.id} => ${document.data}")
                            }
                        }
                        .addOnFailureListener { exception ->
                            Log.w("USER GET ERROR", "Error getting documents.", exception)
                        }
                }
            }
            .addOnFailureListener { exception ->
                Log.w("USER GET ERROR", "Error getting documents.", exception)
            }
    }
}

class HomeViewModel() : ViewModel() {

    private fun <K, V> lazyMap(initializer: (K) -> V): Map<K, V> {
        val map = mutableMapOf<K, V>()
        return map.withDefault { key ->
            val newValue = initializer(key)
            map[key] = newValue
            return@withDefault newValue
        }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

//    private val workouts: Map<Parameters, LiveData<Workouts>> = lazyMap { parameters ->
//        val workouts = MutableLiveData<Workouts>()
//        GetWorkoutsOfUser.loadWorkouts(parameters) { workouts.value = it }
//        return@lazyMap workouts
//    }

    val workouts = MutableLiveData<List<Workout>>().apply {
        value = listOf(
            Workout("Workout 1", mutableListOf(
                Exercise("Bench Press", 1, mutableListOf(
                    Set("Working Set 1"),
                    Set("Working Set 2"),
                    Set("Drop Set 1"),
                    Set("Drop Set 2")
                )
                ),
                Exercise("Shoulder Press", 2, mutableListOf(
                    Set("Working Set 1"),
                    Set("Working Set 2"),
                    Set("Drop Set 1"),
                    Set("Drop Set 2")
                )
                )
            )
            ),
            Workout("Workout 2", mutableListOf(
                Exercise("Leg Press", 1, mutableListOf(
                    Set("Working Set 1"),
                    Set("Working Set 2"),
                    Set("Drop Set 1"),
                    Set("Drop Set 2")
                )
                ),
                Exercise("Hamstring Curls", 2, mutableListOf(
                    Set("Working Set 1"),
                    Set("Working Set 2"),
                )
                )
            )
            ),
        )
    }
}