package com.example.groupgains.ui.record

import android.os.Bundle
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
import com.example.groupgains.databinding.Record1Binding
import com.example.groupgains.ui.create.CreateViewModel

class CreateOne: Fragment() {
    private var _binding: Create1Binding? = null
    private val binding get() = _binding!!

    private val viewModel: CreateViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Create1Binding.inflate(inflater, container, false)
        val root: View = binding.root
        val parentActivity = requireActivity()
        viewModel.initializeActivity(parentActivity)


        return root
    }


    private val create2 = CreateTwo()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.buttonNew.setOnClickListener {
            viewModel.updateWorkoutData(Workout())
            viewModel.updateWorkoutID("")
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, create2)
                commit()
            }
        }

        val workoutLinearLayout: LinearLayout = binding.workoutLinearLayout

        viewModel.workoutsLiveData.observe(viewLifecycleOwner, Observer { workouts ->
            workoutLinearLayout.removeAllViews()
            for (workout in workouts) {

                val workoutItem = LayoutInflater.from(requireContext()).inflate(R.layout.create_workout_item, null)
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
                    Log.d("TEST THIS CLICK", "THIS WAS CLICKED")
                    viewModel.updateWorkoutID(workout.id)
                    viewModel.updateWorkoutData(workout)
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.frame, create2)
                        commit()
                    }
                }
            }
        })

    }

}