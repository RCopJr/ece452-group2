package com.example.groupgains.ui.record

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupgains.databinding.FragmentRecordBinding

class RecordFragment : Fragment() {

    private var _binding: FragmentRecordBinding? = null

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
            binding.start.text = "Resume"
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

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recordViewModel =
            ViewModelProvider(this).get(RecordViewModel::class.java)

        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val exerciseList = arrayListOf<Exercise>(
            Exercise("Bench Press"),
            Exercise("Shoulder Press"),
            Exercise("Bicep Curls"),
            Exercise("Lateral Raises")
        )

        val adapter = ExercisesAdapter(exerciseList)

        binding.rvExercises.adapter = adapter
        binding.rvExercises.layoutManager = LinearLayoutManager(this.context)


        val textView: TextView = binding.textRecord
        recordViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        binding.start.setOnClickListener {
            startTimer()
        }
        binding.stop.setOnClickListener {
            stopTimer()
        }
        binding.reset.setOnClickListener {
            resetTimer()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}