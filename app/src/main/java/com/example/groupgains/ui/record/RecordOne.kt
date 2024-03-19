package com.example.groupgains.ui.record

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.groupgains.R
import com.example.groupgains.databinding.FragmentRecordBinding
import com.example.groupgains.databinding.Record1Binding

class RecordOne: Fragment() {
    private var _binding: Record1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Record1Binding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonContain: LinearLayout = binding.buttonContainer
        buttonContain.setPadding(150,0,0,0)


//        val rec2 = RecordTwo()
//        addButtons(5, buttonContain, rec2)

        return root
    }

    // Using the activityViewModels() Kotlin property delegate from the
    // fragment-ktx artifact to retrieve the ViewModel in the activity scope.
    private val viewModel: RecordViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val rec2 = RecordTwo()
        val buttonContain: LinearLayout = binding.buttonContainer
        buttonContain.setPadding(150,0,0,0)

        viewModel.workouts.observe(viewLifecycleOwner, Observer { data ->
            val workouts = mutableListOf<String>()
            for (workout in data) {
                workouts.add(workout.title)
            }
            for (i in workouts.indices){
                val button = Button(requireContext())
                button.text = workouts[i]

                val params = ViewGroup.LayoutParams(750, 250)
                button.layoutParams = params

                buttonContain.addView(button)

                button.setOnClickListener {
                    onWorkoutClicked(data[i])
                    parentFragmentManager.beginTransaction().apply {
                        replace(R.id.frame, rec2)
                        commit()
                    }
                }
            }
        })
    }

    private fun onWorkoutClicked (workout: Workout) {
        viewModel.selectWorkout(workout)
    }

    private fun addButtons(data: Int, container: LinearLayout, page: RecordTwo) {
        val workouts = listOf<String>("Leg day", "Chest Day", "Arm Day", "Push-pull",
            "Workout", "Workout", "Workout","Workout", "Workout", "Workout")
        for (i in 1..data){
            val button = Button(requireContext())
            button.text = workouts[i-1]

            val params = ViewGroup.LayoutParams(750, 250)
            button.layoutParams = params

            container.addView(button)

            button.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.frame, page)
                    commit()
                }
            }
        }
    }

}