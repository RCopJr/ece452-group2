package com.example.groupgains.ui.record

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.groupgains.R
import com.example.groupgains.data.Workout
import com.example.groupgains.databinding.Create1Binding
import com.example.groupgains.databinding.Create2Binding
import com.example.groupgains.databinding.CreateNewExerciseBinding
import com.example.groupgains.databinding.CreateNewSetBinding
import com.example.groupgains.databinding.Record1Binding
import com.example.groupgains.ui.create.CreateViewModel

class CreateTwo: Fragment() {
    private var _binding: Create2Binding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Create2Binding.inflate(inflater, container, false)
        val root: View = binding.root


        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val buttonC = binding.buttonCancel
        val buttonA = binding.buttonAdd
        val buttonS = binding.buttonSave
        val create1 = CreateOne()

        buttonC.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, create1)
                commit()
            }
        }


        val newExerciseContainer = binding.editExercisesContainer

        buttonA.setOnClickListener {
            viewModel.addExercise()
        }

        buttonS.setOnClickListener {
            viewModel.saveWorkout()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, create1)
                commit()
            }
        }

        //Builds the edit form for the workout
        viewModel.workoutLiveData.observe(viewLifecycleOwner, Observer { workoutData ->
            newExerciseContainer.removeAllViews()
            Log.d("WORKOUT DATA", "$workoutData")
            if (workoutData != null) {
                binding.etWorkoutTitle.setText(workoutData.title)
                binding.etWorkoutTitle.addTextChangedListener(object : TextWatcher {
                    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                        // This method is called before the text is changed.
                    }

                    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                        // This method is called when the text is changed.
                        val newText = s.toString()
                        viewModel.editWorkoutTitle(newText)
                        // Do something with the new text
                    }

                    override fun afterTextChanged(s: Editable?) {
                        // This method is called after the text has changed.
                    }
                })

                for (exercise in workoutData.exercises) {
                    val newExerciseBinding = CreateNewExerciseBinding.inflate(layoutInflater, newExerciseContainer, false)
                    newExerciseBinding.etExerciseTitle.setText(exercise.title)
                    newExerciseBinding.etExerciseTitle.addTextChangedListener(object : TextWatcher {
                        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                            // This method is called before the text is changed.
                        }

                        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                            // This method is called when the text is changed.
                            val newText = s.toString()
                            viewModel.editExerciseTitle(newText, exercise)
                            // Do something with the new text
                        }

                        override fun afterTextChanged(s: Editable?) {
                            // This method is called after the text has changed.
                        }
                    })
                    newExerciseContainer.addView(newExerciseBinding.root)
                    val setContainer = newExerciseBinding.editSetsContainer
                    for (set in exercise.sets) {
                        val newSetBinding = CreateNewSetBinding.inflate(layoutInflater, setContainer, false)
                        newSetBinding.etSetTitle.setText(set.title)
                        newSetBinding.etSetTitle.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                // This method is called before the text is changed.
                            }

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                // This method is called when the text is changed.
                                val newText = s.toString()
                                viewModel.editSetTitle(newText, set)
                                // Do something with the new text
                            }

                            override fun afterTextChanged(s: Editable?) {
                                // This method is called after the text has changed.
                            }
                        })
                        setContainer.addView(newSetBinding.root)
                    }
                    newExerciseBinding.btnAddSet.setOnClickListener {
                        viewModel.addSet(exercise)
                    }

                }
            }
        })

    }

}