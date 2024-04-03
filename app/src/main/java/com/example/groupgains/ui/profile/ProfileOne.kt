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
import android.text.TextWatcher
import android.text.Editable

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

        //get the username
        viewModel.user.observe(viewLifecycleOwner, Observer { user -> 
            val userName = user.userName
            Log.d("Load UserName", "${userName}")
            Log.d("Load UserName model", "${viewModel}")
            Log.d("Load UserName model", "${viewModel.user}")
            Log.d("Load UserName model value", "${viewModel.user.value}")

            binding.textEdit1.setText(userName);

        })

        binding.textEdit1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.updateNameInDb(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //
            }
        })

    }

    private fun onWorkoutClicked (workout: Workout) {
        viewModel.selectWorkout(workout)
    }

}