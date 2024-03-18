package com.example.groupgains.ui.record

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
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

        val buttonContain: LinearLayout = binding.buttonContainer
        buttonContain.setPadding(150,0,0,0)

//        val ex1 = analysisDisplay()
//        val ex2 = analysisDisplay(exerciseName = "Pull-ups")
//        val ex3 = analysisDisplay(exerciseName = "Squat")
//        val ex4 = analysisDisplay(exerciseName = "OHP", numberOfReps = 6, timeElapsed = 32)
//        val ex5 = analysisDisplay(exerciseName = "Lateral raises", numberOfReps = 12, timeElapsed = 45)
//        val ex6 = analysisDisplay(exerciseName = "Leg Extensions")
//        val ex7 = analysisDisplay(exerciseName = "Bicep Curls")
//        val data: List<analysisDisplay> =  listOf(ex1, ex2, ex3, ex4, ex5, ex6, ex7)
//
////        val data =  mutableListOf<analysisDisplay>()
////        for (i in 0..10){
////            data.add(analysisDisplay())
////        }
//
//        addButtons(data, buttonContain)

        return root
    }

    private val viewModel: RecordViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.text.observe(viewLifecycleOwner, Observer { text ->
            val buttonContain: LinearLayout = binding.buttonContainer
            buttonContain.setPadding(150,0,0,0)

            val ex1 = analysisDisplay()
            val ex2 = analysisDisplay(exerciseName = text)
            val ex3 = analysisDisplay(exerciseName = text)
            val ex4 = analysisDisplay(exerciseName = text, numberOfReps = 6, timeElapsed = 32)
            val ex5 = analysisDisplay(exerciseName = text, numberOfReps = 12, timeElapsed = 45)
            val ex6 = analysisDisplay(exerciseName = text)
            val ex7 = analysisDisplay(exerciseName = text)
            val data: List<analysisDisplay> =  listOf(ex1, ex2, ex3, ex4, ex5, ex6, ex7)

//        val data =  mutableListOf<analysisDisplay>()
//        for (i in 0..10){
//            data.add(analysisDisplay())
//        }

            addButtons(data, buttonContain)
        })
    }

    private fun addButtons(data: List<analysisDisplay>, container: LinearLayout) {
        for (i in 0..data.size - 1){
            val button = Button(requireContext())
            button.text = data[i].exerciseName

            val params = ViewGroup.LayoutParams(750, 250)
            button.layoutParams = params

            container.addView(button)

            button.setOnClickListener {
                if (data[i].displayDefault){
                    button.isAllCaps = false
                    button.setLineSpacing(0f, 1.5f)
                    button.text = "Number of Reps: " + data[i].numberOfReps.toString() + "\n" +
                            "Time Elapsed: " + data[i].timeElapsed.toString()
                    data[i].displayDefault = false
                }
                else{
                    button.isAllCaps = true
                    button.text = data[i].exerciseName
                    data[i].displayDefault = true
                }
            }
        }
    }

}




// TODO: Create a separate data class for analysisDisplay
data class analysisDisplay(
    val exerciseName: String = "Bench Press",
    val numberOfReps: Int = 8,
    val timeElapsed: Int = 45,
    var displayDefault: Boolean = true,
)