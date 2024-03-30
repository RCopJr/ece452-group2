//package com.example.groupgains.ui.create
//
//import android.os.Bundle
//import android.util.Log
//import android.view.LayoutInflater
//import android.widget.EditText
//import android.widget.LinearLayout
//import androidx.activity.viewModels
//import androidx.appcompat.app.AppCompatActivity
//import androidx.lifecycle.Observer
//import com.example.groupgains.databinding.Create2Binding
//import com.example.groupgains.databinding.CreateNewExerciseBinding
//import com.example.groupgains.databinding.CreateNewSetBinding
//
//class CreateTwoActivity: AppCompatActivity() {
//
//    private lateinit var binding: Create2Binding
//    private val viewModel: CreateViewModel by viewModels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = Create2Binding.inflate(layoutInflater)
//
//        setContentView(binding.root)
//
//        val buttonC = binding.buttonCancel
//        val buttonA = binding.buttonAdd
//        val buttonS = binding.buttonSave
//
//        buttonC.setOnClickListener {
//            viewModel.goToCreate(this)
//        }
//
//
//        val newExerciseContainer = binding.editExercisesContainer
//
//        buttonA.setOnClickListener {
//            viewModel.addExercise()
//        }
//
//         buttonS.setOnClickListener {
//             viewModel.goToCreate(this)
//          }
//
//        //Builds the edit form for the workout
//        viewModel.workoutLiveData.observe(this, Observer { workoutData ->
//            if (workoutData != null) {
//                Log.d("WORKOUT DATA", "$workoutData")
//                if (workoutData!!.title != "") {
//                    binding.etWorkoutTitle.setText(workoutData.title)
//                }
//                for (exercise in workoutData.exercises) {
//                    val newExerciseBinding = CreateNewExerciseBinding.inflate(layoutInflater, newExerciseContainer, false)
//                    newExerciseBinding.etExerciseTitle.setText(exercise.title)
//                    newExerciseContainer.addView(newExerciseBinding.root)
//                    val setContainer = newExerciseBinding.editSetsContainer
//                    for (set in exercise.sets) {
//                        val newSetBinding = CreateNewSetBinding.inflate(layoutInflater, setContainer, false)
//                        newSetBinding.etSetTitle.setText(set.title)
//                        setContainer.addView(newSetBinding.root)
//                    }
//                    newExerciseBinding.btnAddSet.setOnClickListener {
//                        viewModel.addSet(exercise)
//                    }
//
//                }
//            }
//        })
//    }
//
//    fun add_exercise(container: LinearLayout, inflater: LayoutInflater) {
//        val binding2 = CreateNewExerciseBinding.inflate(inflater, container, false)
//        container.addView(binding2.root)
//
//        val buttonD = binding2.buttonDel
//        buttonD.setOnClickListener {
//            container.removeView(binding2.root)
//        }
//    }
//
//    fun add_set(container: LinearLayout, inflater: LayoutInflater) {
//        val binding2 = CreateNewSetBinding.inflate(inflater, container, false)
//        container.addView(binding2.root)
//
//    }
//}
//
