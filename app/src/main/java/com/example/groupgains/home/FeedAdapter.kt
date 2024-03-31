package com.example.groupgains.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R
import com.example.groupgains.data.Workout


class FeedAdapter(var workoutList: List<Workout>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    
    class FeedViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val userName: TextView = v.findViewById(R.id.userName)
        val workoutName: TextView = v.findViewById(R.id.workoutName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.feed_card, parent, false)
        return FeedViewHolder(v)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val myData = workoutList[position]
        holder.userName.text = myData.title
        holder.workoutName.text = myData.title
    }

    override fun getItemCount() = workoutList.size
}