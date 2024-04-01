package com.example.groupgains.ui.record

import android.os.Bundle
import android.os.Handler
import android.os.Looper
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


        return root
    }

    private val viewModel: RecordViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedWorkout.observe(viewLifecycleOwner) { selectedWorkout ->

            val exerciseList = mutableListOf<Exercise>()
            if (selectedWorkout != null) {
                for (exercise in selectedWorkout.exercises) {
                    exerciseList.add(exercise)
                }
            }

            val adapter = ExercisesAdapter(exerciseList, viewModel)

            binding.rvExercises.adapter = adapter
            binding.rvExercises.layoutManager = LinearLayoutManager(this.context)
        }
    }
}