package com.example.groupgains.ui.record

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R

class SetAdapter(private val setlist : MutableList<Set>) : RecyclerView.Adapter<SetAdapter.MyViewHolder>() {
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val setTitle: TextView

        init {
            // Define click listener for the ViewHolder's View
            setTitle = itemView.findViewById(R.id.setTitle)
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
        holder.setTitle.text = setlist[position].title
    }
}