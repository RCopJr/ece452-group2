package com.example.groupgains.ui.record

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RecordViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is record Fragment"
    }
    val text: LiveData<String> = _text

    private val mutableSelectedExercise = MutableLiveData<Exercise>()
    val selectedExercise: LiveData<Exercise> get() = mutableSelectedExercise

    fun selectExercise(exercise: Exercise) {
        mutableSelectedExercise.value = exercise
    }

    val exercises = MutableLiveData<List<Exercise>>().apply {
        value = listOf(Exercise("test 1"), Exercise("test 2"), Exercise("test 3"))
    }

}