package com.example.groupgains.ui.create

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.groupgains.R
import com.example.groupgains.databinding.FragmentCreateBinding

class CreateFragment : Fragment() {

    private var _binding: FragmentCreateBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val createViewModel =
            ViewModelProvider(this).get(CreateViewModel::class.java)

        _binding = FragmentCreateBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val create1 = CreateOne()

        parentFragmentManager.beginTransaction().apply {
            replace(R.id.frame, create1)
            commit()
        }

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}