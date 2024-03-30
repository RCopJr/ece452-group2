package com.example.groupgains.ui.create

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupgains.data.Exercise
import com.example.groupgains.data.Workout
import javax.inject.Inject

class CreateViewModel @Inject constructor(): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is create Fragment"
    }

    val workoutIDLiveData: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val workoutLiveData = MutableLiveData<Workout>()

    fun updateWorkoutID(newValue: String) {
        workoutIDLiveData.value = newValue
    }

    fun addExercise() {
        val currentWorkoutData = workoutLiveData.value
        currentWorkoutData?.exercises?.add(Exercise())
    }

    fun goToCreateTwo(context: Activity) {
        context.startActivity(Intent(context, CreateTwoActivity::class.java))
        context.finish()
    }

    fun goToCreate(context: Activity) {
        context.startActivity(Intent(context, CreateActivity::class.java))
        context.finish()
    }
}