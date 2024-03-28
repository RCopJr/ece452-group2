package com.example.groupgains.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.example.groupgains.R
import com.example.groupgains.databinding.Create1Binding
import com.example.groupgains.databinding.CreateWorkoutListBinding


class CreateOne: Fragment() {
    private var _binding: Create1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Create1Binding.inflate(inflater, container, false)
        val root: View = binding.root

        val button = binding.buttonNew

        val create2 = CreateTwo()

        button.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, create2)
                commit()
            }
        }

        val contain: LinearLayout = binding.workoutsContainer




        addList(21, contain, inflater)

        return root
    }

    private fun addList(data: Int, container: LinearLayout, inflater: LayoutInflater) {

        for (i in 1..data){
            val binding = CreateWorkoutListBinding.inflate(inflater, container, false)
            binding.setTitle.text = "Workout $i"
            binding.setTitle.textSize = 20.toFloat()
            container.addView(binding.root)

            val button = binding.editButton

            button.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.frame, CreateTwo())
                    commit()
                }
            }

        }
    }
}
