package com.example.groupgains.home

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R
import de.hdodenhof.circleimageview.CircleImageView
import com.bumptech.glide.Glide
import com.example.groupgains.data.User


class FriendAdapter(var friendList: List<User>, private val viewModel: HomeViewModel) : RecyclerView.Adapter<FriendAdapter.FriendViewHolder>() {

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: CircleImageView = itemView.findViewById(R.id.profileImage)
        val friendName: TextView = itemView.findViewById(R.id.friendName)
        val friendButton: Button = itemView.findViewById(R.id.friendButton)
        var friendId: String = ""
        var buttonSet: Boolean = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_card, parent, false)
        return FriendViewHolder(view)
    }

    fun displayButton(holder: FriendViewHolder) {
        if (holder.buttonSet) {
            holder.friendButton.text = "Friends"
            holder.friendButton.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.teal_500)
            )
        } else {
            holder.friendButton.text = "Add Friend"
            holder.friendButton.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.teal_200)
            )
        }
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendList[position]
        holder.friendName.text = friend.userName
        holder.friendId = friend.user_id

        val userId = viewModel.user_id.value ?: ""
        val friendsString = friend.friends.toString()

        holder.buttonSet = friendsString.contains(userId)
        displayButton(holder)

        holder.friendButton.setOnClickListener {
            viewModel.handleFriendClick(holder.friendId)
            holder.buttonSet = !holder.buttonSet
            displayButton(holder)
        }

    }

    override fun getItemCount() = friendList.size


}