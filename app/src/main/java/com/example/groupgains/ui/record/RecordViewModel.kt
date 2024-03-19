package com.example.groupgains.ui.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecordViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is record Fragment"
    }
    val text: LiveData<String> = _text

    private val mutableSelectedWorkout = MutableLiveData<Workout>()
    val selectedWorkout: LiveData<Workout> get() = mutableSelectedWorkout

    fun selectWorkout(workout: Workout) {
        mutableSelectedWorkout.value = workout
    }

    val workouts = MutableLiveData<List<Workout>>().apply {
        value = listOf(
            Workout("Workout 1", mutableListOf(
                Exercise("Exercise 1", 1),
                Exercise("Exercise 2", 2)
                )
            ),
            Workout("Workout 2", mutableListOf(
                Exercise("Exercise 3", 3),
                Exercise("Exercise 4", 4)
                )
            ),
        )
    }

}