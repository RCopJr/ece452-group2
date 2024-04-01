package com.example.groupgains.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groupgains.R
import com.example.groupgains.databinding.Record3Binding
import com.example.groupgains.databinding.RecordStatsBinding

//TODO: Create RecyclerView for Exercises in results page
class RecordThree: Fragment() {
    private var _binding: Record3Binding? = null
    private val binding get() = _binding!!

    private val viewModel: RecordViewModel by activityViewModels()

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
            viewModel.saveSessionToDb()
            parentFragmentManager.beginTransaction().apply {
                replace(R.id.frame, record1)
                commit()
            }
        }



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val statContainer: LinearLayout = binding.statsContainer
        addStats(statContainer)

        val btnSetEfficiency = binding.btnSetEfficiency

        btnSetEfficiency.setOnClickListener {
            val newEfficiency = binding.etSetEfficiency
            viewModel.handleUpdateEfficiency(newEfficiency.text.toString())
        }

        val btnSetFeedback = binding.btnSetFeedback

        btnSetFeedback.setOnClickListener {
            val newFeedback= binding.etSetFeedback
            viewModel.handleUpdateFeedback(newFeedback.text.toString())
        }
    }
    private fun addStats(container: LinearLayout) {
        //CARD FOR VOLUME
        val volumeItem = LayoutInflater.from(requireContext()).inflate(R.layout.record_stats, null)
        val volumeTitle = volumeItem.findViewById<TextView>(R.id.statTitle)
        val volumeValue = volumeItem.findViewById<TextView>(R.id.statDescription)
        volumeTitle.text = "Volume"
        volumeValue.text = viewModel.volume.value.toString()

        container.addView(volumeItem, container.childCount)

        //CARD FOR TIME
        val timeItem = LayoutInflater.from(requireContext()).inflate(R.layout.record_stats, null)
        val timeTitle = timeItem.findViewById<TextView>(R.id.statTitle)
        val timeValue = timeItem.findViewById<TextView>(R.id.statDescription)
        timeTitle.text = "Time"
        timeValue.text = viewModel.totalTime.value.toString()

        container.addView(timeItem, container.childCount)
    }
}