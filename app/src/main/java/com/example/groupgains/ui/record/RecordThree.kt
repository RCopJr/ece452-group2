package com.example.groupgains.ui.record

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.groupgains.R
import com.example.groupgains.databinding.Record1Binding
import com.example.groupgains.databinding.Record3Binding
import com.example.groupgains.databinding.RecordStatsBinding

//TODO: Create RecyclerView for Exercises in results page
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


        val button = binding.button2
        val record1 = RecordOne()
        button.setOnClickListener {
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, record1)
                commit()
            }
        }

        val statContain: LinearLayout = binding.statsContainer

        addStats(statContain, inflater)

        return root
    }

    private val viewModel: RecordViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.text.observe(viewLifecycleOwner, Observer { text ->

        })
    }
    private fun addStats(container: LinearLayout, inflater: LayoutInflater) {
//        val volume_binding = LayoutInflater.from(requireContext()).inflate(R.layout.record_stats, null)
//        val volTitle = volume_binding.findViewById<TextView>(R.id.setTitle)
        //CARD FOR VOLUME
        val volume_binding = RecordStatsBinding.inflate(inflater, container, false)
        volume_binding.statTitle.text = "Volume"
        volume_binding.statDescription.text = "36500000 lb in 2 hours"
        container.addView(volume_binding.root)

        //CARD FOR TIME
        val time_binding = RecordStatsBinding.inflate(inflater, container, false)
        time_binding.statTitle.text = "Time"
        time_binding.statDescription.text = "3hr 4 min"
        container.addView(time_binding.root)
    }
}




// TODO: Create a separate data class for analysisDisplay
data class analysisDisplay(
    val exerciseName: String = "Bench Press",
    val numberOfReps: Int = 8,
    val timeElapsed: Int = 45,
    var displayDefault: Boolean = true,
)