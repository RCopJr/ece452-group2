package com.example.groupgains.ui.record

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.groupgains.R

class RecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.title = "Record Workout Session"
            actionBar.setDisplayHomeAsUpEnabled(true)
        }



    }
}