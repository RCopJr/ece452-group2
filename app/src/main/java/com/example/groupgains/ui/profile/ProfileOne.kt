package com.example.groupgains.ui.profile

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
import com.example.groupgains.databinding.Profile1Binding

class ProfileOne: Fragment() {
    private var _binding: Profile1Binding? = null
    private val binding get() = _binding!!

    private val viewModel: ProfileViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Profile1Binding.inflate(inflater, container, false)
        val root: View = binding.root
        val parentActivity = requireActivity()
        viewModel.initializeActivity(parentActivity)

        return root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // val rec2 = ProfileTwo()
        val workoutLinearLayout: LinearLayout = binding.workoutLinearLayout

        // viewModel.workoutsLiveData.observe(viewLifecycleOwner, Observer { workouts ->
        //     workoutLinearLayout.removeAllViews()
        //     for (workout in workouts) {

        //         val workoutItem = LayoutInflater.from(requireContext()).inflate(R.layout.workout_item, null)
        //         val workoutTitle = workoutItem.findViewById<TextView>(R.id.workoutTitle)
        //         workoutTitle.text = workout.title

        //         var exerciseDescription = ""
        //         for (exercise in workout.exercises) {
        //             exerciseDescription = exerciseDescription + exercise.title + " x " + exercise.numSets + ", "
        //         }
        //         exerciseDescription = exerciseDescription.dropLast(2)
        //         val workoutExerciseDescription = workoutItem.findViewById<TextView>(R.id.workoutExerciseDesc)
        //         workoutExerciseDescription.text = exerciseDescription
        //         workoutLinearLayout.addView(workoutItem, workoutLinearLayout.childCount)

        //         workoutItem.setOnClickListener {
        //             Log.d("TEST THIS CLICK", "THIS WAS CLICKED")
        //             onWorkoutClicked(workout)
        //             parentFragmentManager.beginTransaction().apply {
        //                 replace(R.id.frame, rec2)
        //                 commit()
        //             }
        //         }
        //     }
        // })
    }

    private fun onWorkoutClicked (workout: Workout) {
        viewModel.selectWorkout(workout)
    }


    // private fun addButtons(data: Int, container: LinearLayout, page: RecordTwo) {
    //     val workouts = listOf<String>("Leg day", "Chest Day", "Arm Day", "Push-pull",
    //         "Workout", "Workout", "Workout","Workout", "Workout", "Workout")
    //     for (i in 1..data){
    //         val button = Button(requireContext())
    //         button.text = workouts[i-1]

    //         val params = ViewGroup.LayoutParams(750, 250)
    //         button.layoutParams = params

    //         container.addView(button)

    //         button.setOnClickListener {
    //             parentFragmentManager.beginTransaction().apply {
    //                 replace(R.id.frame, page)
    //                 commit()
    //             }
    //         }
    //     }
    // }

}