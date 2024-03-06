package com.example.groupgains.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.groupgains.R
import com.example.groupgains.databinding.FragmentRecordBinding


class RecordFragment : Fragment() {

    private var _binding: FragmentRecordBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val recordViewModel =
            ViewModelProvider(this).get(RecordViewModel::class.java)

        _binding = FragmentRecordBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val record1 = RecordOne()
        val button = binding.button2

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.frame, record1)
            commit()
        }

        button.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, record1)
                commit()
            }
        }


        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}