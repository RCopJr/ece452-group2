package com.example.groupgains.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.groupgains.R
import com.example.groupgains.databinding.Record1Binding
import com.example.groupgains.databinding.Record3Binding

class RecordThree: Fragment() {
    private var _binding: Record3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Record3Binding.inflate(inflater, container, false)
        val root: View = binding.root

        val butt = binding.button3

        val rec1 = RecordOne()

        butt.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, rec1)
                commit()
            }
        }

        return root
    }
}