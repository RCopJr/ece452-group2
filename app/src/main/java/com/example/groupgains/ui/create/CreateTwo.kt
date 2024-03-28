package com.example.groupgains.ui.create

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet.Constraint
import androidx.fragment.app.Fragment
import com.example.groupgains.R

import com.example.groupgains.databinding.Create2Binding
import com.example.groupgains.databinding.CreateWorkoutDetailsBinding
import com.example.groupgains.databinding.CreateWorkoutListBinding


class CreateTwo: Fragment() {
    private var _binding: Create2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Create2Binding.inflate(inflater, container, false)
        val root: View = binding.root

        val buttonC = binding.buttonCancel
        val buttonA = binding.buttonAdd
        val buttonS = binding.buttonSave

        val contain = binding.workoutsEditionContainer


        buttonC.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, CreateOne())
                commit()
            }
        }

        buttonA.setOnClickListener {
            add_exercise(contain, inflater)
        }

        buttonS.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, CreateOne())
                commit()
            }
        }

        return root
    }

    fun add_exercise(container: LinearLayout, inflater: LayoutInflater) {
        val binding2 = CreateWorkoutDetailsBinding.inflate(inflater, container, false)
        container.addView(binding2.root)

        val buttonD = binding2.buttonDel
        buttonD.setOnClickListener {
            container.removeView(binding2.root)
        }
    }
}