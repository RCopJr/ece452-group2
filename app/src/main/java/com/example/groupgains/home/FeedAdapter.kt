package com.example.groupgains.home

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R
import com.example.groupgains.data.SessionData


class FeedAdapter(var feedList: List<SessionData>) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    
    class FeedViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val userName: TextView = v.findViewById(R.id.userName)
        val workoutName: TextView = v.findViewById(R.id.workoutName)
        val timeValue: TextView = v.findViewById(R.id.timeValue)
        val volumeValue: TextView = v.findViewById(R.id.volumeValue)
        val efficiencyValue: TextView = v.findViewById(R.id.efficiencyValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.feed_card, parent, false)
        return FeedViewHolder(v)
    }

    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val myData = feedList[position]
        holder.userName.text = myData.userName
        holder.workoutName.text = myData.workoutName
        holder.timeValue.text = myData.stats.totalTime
        holder.volumeValue.text = "${myData.stats.volume} lb"
        holder.efficiencyValue.text = "${myData.stats.volume} %"
    }

    override fun getItemCount() = feedList.size
}