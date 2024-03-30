package com.example.groupgains.ui.record

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R
import com.example.groupgains.data.Exercise

class ExercisesAdapter(private val exerciseList : MutableList<Exercise>) : RecyclerView.Adapter<ExercisesAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseTitle: TextView

        init {
            // Define click listener for the ViewHolder's View
            exerciseTitle = itemView.findViewById(R.id.textView4)
        }

        fun bind(exercise: Exercise) {
//            var amountOfSets = exercise.sets.size.toString()
//            Log.d("AMOUNT OF SETS", amountOfSets)
            val childSetsAdapter = SetAdapter(exercise.sets)
            val setsRecyclerView = itemView.findViewById<RecyclerView>(R.id.rvSets)
            setsRecyclerView.layoutManager = LinearLayoutManager(itemView.context, LinearLayoutManager.VERTICAL,false)
            setsRecyclerView.adapter = childSetsAdapter

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
        holder.bind(exerciseList[position])
    }
}