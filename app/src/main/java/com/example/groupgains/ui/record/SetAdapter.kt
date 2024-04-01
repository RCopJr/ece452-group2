package com.example.groupgains.ui.record

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R
import com.example.groupgains.data.Set

class SetAdapter(private val setlist : MutableList<Set>, private val viewModel: RecordViewModel) : RecyclerView.Adapter<SetAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setTitle: TextView
        val setWeight: TextView
        val setReps: TextView
        val setCheckBox: CheckBox

        init {
            // Define click listener for the ViewHolder's View
            setTitle = itemView.findViewById(R.id.setTitle)
            setWeight = itemView.findViewById(R.id.setWeight)
            setReps = itemView.findViewById(R.id.setReps)
            setCheckBox = itemView.findViewById(R.id.setCheckBox)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.set_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
//        Log.d("SET LIST SIZE", setlist.size.toString())
        return setlist.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        Log.d("SET TITLE", setlist[position].title)

        val set = setlist[position]

        holder.setTitle.text = set.title
        holder.setWeight.text = set.weight
        holder.setReps.text = set.reps

        holder.setCheckBox.setOnCheckedChangeListener { _, isChecked ->
            viewModel.handleSetChecked(set, isChecked)
        }


    }
}