package com.example.groupgains.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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

        val butt = binding.button11

        val rec2 = RecordTwo()

        butt.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, rec2)
                commit()
            }
        }

        return root
    }
}