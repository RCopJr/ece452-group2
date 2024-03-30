package com.example.groupgains.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.groupgains.databinding.Create2Binding
import com.example.groupgains.databinding.CreateNewExerciseBinding
import com.example.groupgains.databinding.CreateNewSetBinding

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

        val newExerciseContainer = binding.workoutsEditionContainer

        buttonC.setOnClickListener {
            viewModel.goToCreate(this)
        }

        buttonA.setOnClickListener {
            add_exercise(newExerciseContainer, layoutInflater)
//            add_set()
        }

         buttonS.setOnClickListener {
             viewModel.goToCreate(this)
          }

//        viewModel.workoutLiveData.observe(this, Observer { workoutData ->
//            for (exercise in workoutData.exercises) {
//                val exerciseFieldsBinding = CreateWorkoutDetailsBinding.inflate(layoutInflater, contain, false)
//                exerciseFieldsBinding.editButton.setText(exercise.title)
//
//            }
//        })

    }

    fun add_exercise(container: LinearLayout, inflater: LayoutInflater) {
        val binding2 = CreateNewExerciseBinding.inflate(inflater, container, false)
        container.addView(binding2.root)

        val buttonD = binding2.buttonDel
        buttonD.setOnClickListener {
            container.removeView(binding2.root)
        }
    }

    fun add_set(container: LinearLayout, inflater: LayoutInflater) {
        val binding2 = CreateNewSetBinding.inflate(inflater, container, false)
        container.addView(binding2.root)

    }
}

