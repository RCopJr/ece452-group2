package com.example.groupgains.notifications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R
import de.hdodenhof.circleimageview.CircleImageView
import com.example.groupgains.data.User


class FriendAdapterNotifs(var friendList: List<User>, private val viewModel: NotificationsViewModel) : RecyclerView.Adapter<FriendAdapterNotifs.FriendViewHolder>() {

    class FriendViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileImage: CircleImageView = itemView.findViewById(R.id.profileImage)
        val friendName: TextView = itemView.findViewById(R.id.friendName)
        val addButton: Button = itemView.findViewById(R.id.addButton)
        val rejectButton: Button = itemView.findViewById(R.id.rejectButton)
        var friendId: String = ""
        var buttonSet: Boolean = false


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.friend_request_card, parent, false)
        return FriendViewHolder(view)
    }

    fun displayButton(holder: FriendViewHolder) {
//        if (holder.buttonSet) {
//            holder.friendButton.text = "Unfriend"
//            holder.friendButton.setBackgroundColor(
//                ContextCompat.getColor(holder.itemView.context, R.color.teal_500)
//            )
//        } else {
//            holder.friendButton.text = "Add Friend"
//            holder.friendButton.setBackgroundColor(
//                ContextCompat.getColor(holder.itemView.context, R.color.teal_500)
//            )
//        }
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        val friend = friendList[position]
        holder.friendName.text = friend.userName
        holder.friendId = friend.user_id

        val userId = viewModel.user_id.value ?: ""
        val friendsString = friend.friends.toString()

        holder.buttonSet = friendsString.contains(userId)
        displayButton(holder)

        holder.addButton.setOnClickListener {
            viewModel.handleFriendAdd(holder.friendId)
            holder.itemView.visibility = View.GONE;
        }

        holder.rejectButton.setOnClickListener {
            viewModel.handleFriendReject(holder.friendId)
            holder.itemView.visibility = View.GONE;
        }

    }

    override fun getItemCount() = friendList.size


}