package com.example.groupgains.ui.record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R

class ExercisesAdapter(private val exerciseList : ArrayList<Exercise>) : RecyclerView.Adapter<ExercisesAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseTitle: TextView

        init {
            // Define click listener for the ViewHolder's View
            exerciseTitle = itemView.findViewById(R.id.textView4)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.exercise_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return exerciseList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.exerciseTitle.text = exerciseList[position].title
    }
}