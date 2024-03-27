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
import androidx.lifecycle.ViewModelProvider
import com.example.groupgains.R
import com.example.groupgains.databinding.FragmentCreateBinding
import com.example.groupgains.databinding.Create1Binding
import com.example.groupgains.ui.record.RecordTwo

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
        val create3 = CreateThree()

        button.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, create2)
                commit()
            }
        }

        val contian: LinearLayout = binding.workoutsContainer

        addButtons(5, contian, create3)

        return root
    }

    private fun addButtons(data: Int, container: LinearLayout, page: CreateThree) {

        for (i in 1..data){
            val lay = LinearLayout(requireContext())
            lay.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT)
            lay.orientation = LinearLayout.HORIZONTAL
            lay.gravity = Gravity.CENTER_VERTICAL

            val text = TextView(requireContext())
            text.text = "Workout"
            text.textSize = 30.toFloat()
            text.setPadding(200, 50, 200, 50)

            val button = Button(requireContext())
            button.text = "Edit"

            button.setOnClickListener {
                parentFragmentManager.beginTransaction().apply {
                    replace(R.id.frame, page)
                    commit()
                }
            }


            val params = ConstraintLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
            params.rightToRight = ConstraintLayout.LayoutParams.RIGHT
            button.layoutParams = params

            lay.addView(text)
            lay.addView(button)
            container.addView(lay)

        }
    }
}
