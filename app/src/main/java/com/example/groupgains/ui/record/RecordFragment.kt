package com.example.groupgains.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.groupgains.databinding.FragmentRecordBinding
import android.content.Intent

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

        val textView: TextView = binding.textRecord
        recordViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val buttonID = binding.recordWorkoutBtn

        buttonID.setOnClickListener {
            val intent = Intent(requireContext(), RecordActivity::class.java)
            startActivity(intent)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}