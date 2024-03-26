package com.example.groupgains.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.groupgains.R
import com.example.groupgains.databinding.FragmentHomeBinding
import com.example.groupgains.ui.record.RecordTwo

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }


    private val viewModel: HomeViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rec2 = RecordTwo()
        val workoutLinearLayout: LinearLayout = binding.workoutLinearLayout

        viewModel.workouts.observe(viewLifecycleOwner, Observer { workouts ->

            for (workout in workouts) {

                val workoutItem = LayoutInflater.from(requireContext()).inflate(R.layout.workout_item, null)
                val workoutTitle = workoutItem.findViewById<TextView>(R.id.workoutTitle)
                workoutTitle.text = workout.title

                var exerciseDescription = ""
                for (exercise in workout.exercises) {
                    exerciseDescription = exerciseDescription + exercise.title + " x " + exercise.numSets + ", "
                }
                exerciseDescription = exerciseDescription.dropLast(2)
                val workoutExerciseDescription = workoutItem.findViewById<TextView>(R.id.workoutExerciseDesc)
                workoutExerciseDescription.text = exerciseDescription
                workoutLinearLayout.addView(workoutItem, workoutLinearLayout.childCount)

                workoutItem.setOnClickListener {
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.frame, rec2)
                        commit()
                    }
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}