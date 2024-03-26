package com.example.groupgains.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupgains.ui.record.Exercise
import com.example.groupgains.ui.record.Set
import com.example.groupgains.ui.record.Workout

class HomeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text

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