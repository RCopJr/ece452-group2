package com.example.groupgains.ui.record

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupgains.R
import com.example.groupgains.data.Exercise
import com.example.groupgains.databinding.CreateNewExerciseBinding
import com.example.groupgains.databinding.CreateNewSetBinding
import com.example.groupgains.databinding.EditExerciseRecordBinding
import com.example.groupgains.databinding.EditSetRecordBinding
import com.example.groupgains.databinding.Record2Binding

class RecordTwo: Fragment(R.layout.record_2) {
    private var _binding: Record2Binding? = null

    private var isRunning = false
    private var timerSeconds = 0

    private val handler = Handler(Looper.getMainLooper())
    private val runnable = object : Runnable {
        override fun run() {
            timerSeconds++
            val hours = timerSeconds / 3600
            val minutes = (timerSeconds % 3600) / 60
            val seconds = timerSeconds % 60

            val time = String.format("%02d:%02d:%02d", hours, minutes, seconds)
            binding.timer.text = time

            handler.postDelayed(this, 1000)
        }
    }

    private fun startTimer() {
        if(!isRunning){
            handler.postDelayed(runnable, 1000)
            isRunning = true
            binding.start.isEnabled = false
            binding.stop.isEnabled = true
            binding.reset.isEnabled = true
        }
    }

    private fun stopTimer(){
        if(isRunning){
            handler.removeCallbacks(runnable)
            isRunning = false
            binding.start.isEnabled = true
            binding.stop.isEnabled = false
            binding.reset.isEnabled = true
        }
    }

    private fun resetTimer(){
        stopTimer()
        timerSeconds = 0
        binding.timer.text = "00:00:00"
        binding.start.isEnabled = true
        binding.reset.isEnabled = false
        binding.start.text = "Start"
    }
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Record2Binding.inflate(inflater, container, false)
        val root: View = binding.root

        val rec1 = RecordOne()
        val rec3 = RecordThree()

        binding.start.setOnClickListener {
            startTimer()
        }
        binding.stop.setOnClickListener {
            viewModel.createSession(binding.timer.text.toString())
            stopTimer()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, rec3)
                commit()
            }
        }
        binding.reset.setOnClickListener {
            resetTimer()
        }
        binding.buttonCancel.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, rec1)
                commit()
            }
        }

        return root
    }

    private val viewModel: RecordViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonAdd.setOnClickListener {
            viewModel.addExercise()
        }

        val newExerciseContainer = binding.editExercisesContainer

        viewModel.selectedWorkout.observe(viewLifecycleOwner, Observer { workoutData ->
            newExerciseContainer.removeAllViews()
            Log.d("WORKOUT DATA", "$workoutData")
            if (workoutData != null) {
                binding.etWorkoutTitle.setText(workoutData.title)

                for (exercise in workoutData.exercises) {
                    val newExerciseBinding = EditExerciseRecordBinding.inflate(layoutInflater, newExerciseContainer, false)
                    newExerciseBinding.etExerciseTitle.setText(exercise.title)
                    newExerciseBinding.buttonDel.setOnClickListener {
                        viewModel.deleteExercise(exercise)
                    }
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
                        val newSetBinding = EditSetRecordBinding.inflate(layoutInflater, setContainer, false)
                        newSetBinding.etSetTitle.setText(set.title)
                        newSetBinding.etSetWeight.setText(set.weight)
                        newSetBinding.etSetReps.setText(set.reps)

                        newSetBinding.setCheckBox.isChecked = set.checked

                        newSetBinding.setCheckBox.setOnCheckedChangeListener { _, isChecked ->
                            viewModel.handleSetChecked(set, isChecked)
//                            newSetBinding.setCheckBox.isChecked = isChecked
                        }

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

                        newSetBinding.etSetWeight.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                // This method is called before the text is changed.
                            }

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                // This method is called when the text is changed.
                                val newText = s.toString()
                                viewModel.editSetWeight(newText, set)
                                // Do something with the new text
                            }

                            override fun afterTextChanged(s: Editable?) {
                                // This method is called after the text has changed.
                            }
                        })

                        newSetBinding.etSetReps.addTextChangedListener(object : TextWatcher {
                            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                                // This method is called before the text is changed.
                            }

                            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                                // This method is called when the text is changed.
                                val newText = s.toString()
                                viewModel.editSetReps(newText, set)
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