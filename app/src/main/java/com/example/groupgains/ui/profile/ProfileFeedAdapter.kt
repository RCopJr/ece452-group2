package com.example.groupgains.ui.profile

import android.annotation.SuppressLint
import android.graphics.Typeface
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.getColor
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R
import com.example.groupgains.data.Reactions
import com.example.groupgains.data.SessionData


class ProfileFeedAdapter(var feedList: List<SessionData>, private val viewModel: ProfileViewModel) : RecyclerView.Adapter<ProfileFeedAdapter.FeedViewHolder>() {
    
    class FeedViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        val userName: TextView = v.findViewById(R.id.userName)
        val workoutName: TextView = v.findViewById(R.id.workoutName)
        val timeValue: TextView = v.findViewById(R.id.timeValue)
        val volumeValue: TextView = v.findViewById(R.id.volumeValue)
        val completionValue: TextView = v.findViewById(R.id.completionValue)
        val setsValue: TextView = v.findViewById(R.id.setsValue)
        val userFeedbackImage: ImageView = v.findViewById(R.id.userFeedbackImage)

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

        // CardView for margin
        val heartCard = v.findViewById<CardView>(R.id.heart)
        val celebrateCard = v.findViewById<CardView>(R.id.celebrate)
        val fireCard = v.findViewById<CardView>(R.id.fire)
        val bicepCard = v.findViewById<CardView>(R.id.bicep)
        val hundredCard = v.findViewById<CardView>(R.id.hundred)
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
        holder.completionValue.text = "${myData.stats.completion} %"
        holder.setsValue.text = "${myData.stats.totalSets} sets"
        val session_id = myData.sessionId

        when (myData.stats.feedback) {
            "1" -> holder.userFeedbackImage.setImageResource(R.drawable.crying_face)
            "2" -> holder.userFeedbackImage.setImageResource(R.drawable.slightly_frowning_face)
            "3" -> holder.userFeedbackImage.setImageResource(R.drawable.neutral_face)
            "4" -> holder.userFeedbackImage.setImageResource(R.drawable.slightly_smiling_face)
            "5" -> holder.userFeedbackImage.setImageResource(R.drawable.smiling_face_with_open_mouth)
            else -> holder.userFeedbackImage.setImageResource(R.drawable.neutral_face)
        }

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

        loadReactionsToUI(holder.heartCard, holder.heartCounter, heart_reactions, userId)
        loadReactionsToUI(holder.bicepCard, holder.bicepCounter, bicep_reactions, userId)
        loadReactionsToUI(holder.hundredCard, holder.hundredCounter, hundred_reactions, userId)
        loadReactionsToUI(holder.fireCard, holder.fireCounter, fire_reactions, userId)
        loadReactionsToUI(holder.celebrateCard, holder.celebrateCounter, celebrate_reactions, userId)
    }

    override fun getItemCount() = feedList.size

    private fun loadReactionsToUI(margin: CardView, counter: TextView, reactions: MutableList<String>, user: String){
        if(user in reactions){
            counter.setTypeface(null, Typeface.BOLD)
            counter.setTextColor(getColor(counter.context,R.color.teal_400))
            margin.setCardBackgroundColor(getColor(counter.context,R.color.teal_200))
        }
        else{
            counter.setTypeface(null, Typeface.NORMAL)
            counter.setTextColor(0xFF000000.toInt())
            margin.setCardBackgroundColor(0xFFFFFFFF.toInt())
        }
    }

}