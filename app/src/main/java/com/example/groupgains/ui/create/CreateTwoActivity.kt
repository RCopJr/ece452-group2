package com.example.groupgains.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.groupgains.databinding.Create2Binding
import com.example.groupgains.databinding.CreateWorkoutDetailsBinding

class CreateTwoActivity: AppCompatActivity() {

    private lateinit var binding: Create2Binding
    private val viewModel: CreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = Create2Binding.inflate(layoutInflater)

        setContentView(binding.root)

        val buttonC = binding.buttonCancel
        val buttonA = binding.buttonAdd
        val buttonS = binding.buttonSave

        val contain = binding.workoutsEditionContainer

        buttonC.setOnClickListener {
            viewModel.goToCreate(this)
        }

        buttonA.setOnClickListener {
            add_exercise(contain, layoutInflater)
        }

         buttonS.setOnClickListener {
             viewModel.goToCreate(this)
          }

    }

    fun add_exercise(container: LinearLayout, inflater: LayoutInflater) {
        val binding2 = CreateWorkoutDetailsBinding.inflate(inflater, container, false)
        container.addView(binding2.root)

        val buttonD = binding2.buttonDel
        buttonD.setOnClickListener {
            container.removeView(binding2.root)
        }
    }
}