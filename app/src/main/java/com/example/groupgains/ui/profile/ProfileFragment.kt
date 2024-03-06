package com.example.groupgains.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.groupgains.databinding.FragmentProfileBinding
import android.widget.TableLayout
import android.widget.TableRow
import android.graphics.Color
import com.example.groupgains.R
import androidx.core.content.ContextCompat
import android.widget.RelativeLayout
import android.widget.LinearLayout

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val profileViewModel =
            ViewModelProvider(this).get(ProfileViewModel::class.java)

        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textProfile
        profileViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

        val tableLayout: TableLayout = binding.tableLayout
    
        profileViewModel.tableData.observe(viewLifecycleOwner, { data: List<TableRowData> ->
            // Clear the table
            tableLayout.removeAllViews()
    
            // Add a row for each element in the data
            for (row in data) {
                val tableRow = TableRow(context).apply {
                    layoutParams = RelativeLayout.LayoutParams(
                        TableRow.LayoutParams.MATCH_PARENT,
                        TableRow.LayoutParams.WRAP_CONTENT
                    ).apply {
                        addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
                    }
                    setPadding(16, 16, 16, 16) // Set padding for the row
                }
    
                val textView1 = TextView(context).apply {
                    text = row.column1
                    textSize = 18f // Set text size
                    setTextColor(Color.BLACK) // Set text color
                }
                val borderDrawable = ContextCompat.getDrawable(requireContext(), R.drawable.border)
                val textView2 = TextView(context).apply {
                    text = row.column2
                    textSize = 18f // Set text size
                    setTextColor(Color.RED) // Set text color
                    setBackgroundResource(R.drawable.border) // Set border
                }
                
                val textView3 = TextView(context).apply {
                    text = row.column3
                    textSize = 18f // Set text size
                    setTextColor(Color.GREEN) // Set text color
                    setBackgroundResource(R.drawable.border) // Set border
                }
    
                tableRow.addView(textView1)
                tableRow.addView(textView2)
                tableRow.addView(textView3)
                tableLayout.addView(tableRow)
                
            }
        })


        val parentLayout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL

            val row1 = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL

                val textView2 = TextView(context).apply {
                text = " Push Day \n Bench Press \n (Barbell), Single Arm \n Tricep Pulldown \n (Cable)"
                textSize = 18f
                setTextColor(Color.BLACK)
                setBackgroundResource(R.drawable.box_background)

                // Get the screen width
                val displayMetrics = resources.displayMetrics
                val screenWidth = displayMetrics.widthPixels

                // Set the dimensions of the TextView
                layoutParams = LinearLayout.LayoutParams(
                    screenWidth / 2 - 10, // Width
                    screenWidth / 2 - 10  // Height
                ).apply {
                    // Set the margins
                    val margin = 16
                    setMargins(margin, margin, margin, margin)
                }
            }

                val textView3 = TextView(context).apply {
                text = " Pull Day \n Barbell Row, Seated \n Row (Cable)"
                textSize = 18f
                setTextColor(Color.BLACK)
                setBackgroundResource(R.drawable.box_background)

                // Get the screen width
                val displayMetrics = resources.displayMetrics
                val screenWidth = displayMetrics.widthPixels

                // Set the dimensions of the TextView
                layoutParams = LinearLayout.LayoutParams(
                    screenWidth / 2 - 10, // Width
                    screenWidth / 2 - 10  // Height
                ).apply {
                    // Set the margins
                    val margin = 16
                    setMargins(margin, margin, margin, margin)
                }
            }

                addView(textView2)
                addView(textView3)
            }

            val row2 = LinearLayout(context).apply {
                orientation = LinearLayout.HORIZONTAL

                val textView4 = TextView(context).apply {
                text = " Leg Day \n Squat (Barbel) \n Hamstring Curl, Leg \n Extensions, Calf \n Raises"
                textSize = 18f
                setTextColor(Color.BLACK)
                setBackgroundResource(R.drawable.box_background)

                // Get the screen width
                val displayMetrics = resources.displayMetrics
                val screenWidth = displayMetrics.widthPixels

                // Set the dimensions of the TextView
                layoutParams = LinearLayout.LayoutParams(
                    screenWidth / 2 - 10, // Width
                    screenWidth / 2 - 10  // Height
                ).apply {
                    // Set the margins
                    val margin = 16
                    setMargins(margin, margin, margin, margin)
                }
            }

                addView(textView4)
            }

            addView(row1)
            addView(row2)
        }

        (root as ViewGroup).addView(parentLayout)

        return root
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}