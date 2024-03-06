package com.example.groupgains.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.groupgains.R
import com.example.groupgains.databinding.Record1Binding
import com.example.groupgains.databinding.Record2Binding

class RecordTwo: Fragment(R.layout.record_2) {
    private var _binding: Record2Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = Record2Binding.inflate(inflater, container, false)
        val root: View = binding.root

        val butt = binding.button22

        val rec3 = RecordThree()

        butt.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, rec3)
                commit()
            }
        }

        return root
    }

}