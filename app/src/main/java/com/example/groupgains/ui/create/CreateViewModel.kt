package com.example.groupgains.ui.create

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class CreateViewModel @Inject constructor(): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is create Fragment"
    }
    val text: LiveData<String> = _text

    fun goToCreateTwo(context: Activity) {
        context.startActivity(Intent(context, CreateTwoActivity::class.java))
        context.finish()
    }

    fun goToCreate(context: Activity) {
        context.startActivity(Intent(context, CreateActivity::class.java))
        context.finish()
    }
}