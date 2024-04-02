package com.example.groupgains.ui.record

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.groupgains.R
import com.example.groupgains.databinding.Record3Binding

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
        val volumeValue = view.findViewById<TextView>(R.id.volumeValue)
        volumeValue.text = "${viewModel.volume.value.toString()} lb"

        val setsValue = view.findViewById<TextView>(R.id.setsValue)
        setsValue.text = "${viewModel.totalSets.value.toString()} sets"

        val timeValue = view.findViewById<TextView>(R.id.timeValue)
        timeValue.text = "${viewModel.totalTime.value.toString()}"

        val completionValue = view.findViewById<TextView>(R.id.completionValue)
        completionValue.text = "${viewModel.completion.value.toString()} %"

        val feedback1ImageView: ImageView = view.findViewById(R.id.feedback1)
        val feedback2ImageView: ImageView = view.findViewById(R.id.feedback2)
        val feedback3ImageView: ImageView = view.findViewById(R.id.feedback3)
        val feedback4ImageView: ImageView = view.findViewById(R.id.feedback4)
        val feedback5ImageView: ImageView = view.findViewById(R.id.feedback5)

        feedback1ImageView.setOnClickListener {
            feedback1ImageView.setBackgroundResource(R.drawable.feedback_item_selected)
            feedback2ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback3ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback4ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback5ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            viewModel.handleUpdateFeedback("1")
        }
        feedback2ImageView.setOnClickListener {
            feedback1ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback2ImageView.setBackgroundResource(R.drawable.feedback_item_selected)
            feedback3ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback4ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback5ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            viewModel.handleUpdateFeedback("2")
        }


        feedback3ImageView.setOnClickListener {
            feedback1ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback2ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback3ImageView.setBackgroundResource(R.drawable.feedback_item_selected)
            feedback4ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback5ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            viewModel.handleUpdateFeedback("3")
        }


        feedback4ImageView.setOnClickListener {
            feedback1ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback2ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback3ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback4ImageView.setBackgroundResource(R.drawable.feedback_item_selected)
            feedback5ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            viewModel.handleUpdateFeedback("4")
        }


        feedback5ImageView.setOnClickListener {
            feedback1ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback2ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback3ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback4ImageView.setBackgroundResource(R.drawable.feedback_item_unselected)
            feedback5ImageView.setBackgroundResource(R.drawable.feedback_item_selected)
            viewModel.handleUpdateFeedback("5")
        }
    }
}