package com.example.groupgains.home

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R
import com.example.groupgains.data.SessionData
import com.example.groupgains.data.Workout


class FeedAdapter(var feedList: List<SessionData>, private val viewModel: HomeViewModel) : RecyclerView.Adapter<FeedAdapter.FeedViewHolder>() {
    
    class FeedViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val userName: TextView = v.findViewById(R.id.userName)
        val workoutName: TextView = v.findViewById(R.id.workoutName)
        val timeValue: TextView = v.findViewById(R.id.timeValue)
        val volumeValue: TextView = v.findViewById(R.id.volumeValue)
        val efficiencyValue: TextView = v.findViewById(R.id.efficiencyValue)

        // Get interactive emoji button
        val heartButton = v.findViewById<LinearLayout>(R.id.emojiHeartLayout)
        val celebrateButton = v.findViewById<LinearLayout>(R.id.emojiCelebrateLayout)
        val fireButton = v.findViewById<LinearLayout>(R.id.emojiFireLayout)
        val hundredButton = v.findViewById<LinearLayout>(R.id.emojiHundredLayout)
        val bicepButton = v.findViewById<LinearLayout>(R.id.emojiBicepLayout)

        // Get emoji counter
        val heartCounter = heartButton.findViewById<TextView>(R.id.emojiHeartVal)
        val celebrateCounter = celebrateButton.findViewById<TextView>(R.id.emojiCelebrateVal)
        val fireCounter = fireButton.findViewById<TextView>(R.id.emojiFireVal)
        val hundredCounter = hundredButton.findViewById<TextView>(R.id.emojiHundredVal)
        val bicepCounter = bicepButton.findViewById<TextView>(R.id.emojiBicepVal)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.feed_card, parent, false)
        return FeedViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val userId = viewModel.user_id.value ?: ""
        val myData = feedList[position]
        holder.userName.text = myData.userName
        holder.workoutName.text = myData.workoutName
        holder.timeValue.text = myData.stats.totalTime
        holder.volumeValue.text = "${myData.stats.volume} lb"
        holder.efficiencyValue.text = "${myData.stats.volume} %"

        // Load arrays
        val heart_reactions = myData.reactions.heart
        val bicep_reactions = myData.reactions.bicep
        val hundred_reactions = myData.reactions.hundred
        val fire_reactions = myData.reactions.fire
        val celebrate_reactions = myData.reactions.celebrate

        holder.heartCounter.text = heart_reactions.size.toString()
        holder.bicepCounter.text = bicep_reactions.size.toString()
        holder.hundredCounter.text = hundred_reactions.size.toString()
        holder.fireCounter.text = fire_reactions.size.toString()
        holder.celebrateCounter.text = celebrate_reactions.size.toString()

        //TODO: Connect to database. Do we need on Destroy?
        holder.heartButton.setOnClickListener{
            if (userId in heart_reactions) {
                holder.heartCounter.text = (heart_reactions.size - 1).toString()
                holder.heartCounter.setTypeface(null, Typeface.NORMAL)
                holder.heartCounter.setTextColor(0xFF000000.toInt())
                heart_reactions.remove(userId)
            }
            else {
                holder.heartCounter.text = (heart_reactions.size + 1).toString()
                holder.heartCounter.setTypeface(null, Typeface.BOLD)
                holder.heartCounter.setTextColor(0xFFFF0000.toInt())
                heart_reactions.add(userId)
            }
        }

    }

    override fun getItemCount() = feedList.size

}