package com.example.groupgains.notifications

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R
import com.example.groupgains.databinding.ActivityHomeBinding
import com.example.groupgains.databinding.ActivityNotificationsBinding
import com.example.groupgains.home.FeedAdapter
import com.example.groupgains.home.FriendAdapter
import com.example.groupgains.home.HomeActivity
import com.example.groupgains.home.HomeViewModel
import com.example.groupgains.ui.create.CreateActivity
import com.example.groupgains.ui.profile.ProfileActivity
import com.example.groupgains.ui.record.RecordActivity
import javax.inject.Inject

class NotificationsActivity @Inject constructor(): AppCompatActivity() {
    private lateinit var binding: ActivityNotificationsBinding
    private val viewModel: NotificationsViewModel by viewModels()

    override fun onDestroy() {
        super.onDestroy()


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationsBinding.inflate(layoutInflater)
        viewModel.initializeActivity(this)

        setContentView(binding.root)

        binding.navView.selectedItemId = R.id.navigation_notification
        binding.navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_create -> {
                    startActivity(Intent(this, CreateActivity::class.java))
                    true
                }

                R.id.navigation_record -> {
                    startActivity(Intent(this, RecordActivity::class.java))
                    true
                }

                R.id.navigation_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }

                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }

                else -> false
            }
        }


        val friendRequestAdapter = FriendAdapterNotifs(emptyList(), viewModel)
        val friendRequestRecyclerView: RecyclerView = findViewById(R.id.friendRecyclerView)
        friendRequestRecyclerView.layoutManager = LinearLayoutManager(this)
        friendRequestRecyclerView.adapter = friendRequestAdapter

        // Setup observer for when posts to display changes
        viewModel.friendRequestsLiveData.observe(this) { friends ->
            Log.d("FRIEND REQUEST LIVE DATA CHANGES", "$friends")
            friendRequestAdapter.friendList = friends
            friendRequestAdapter.notifyDataSetChanged()
        }

        viewModel.loadFriendRequestData()
    }
}