package com.example.groupgains.home

import android.content.Intent
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.groupgains.R
import com.example.groupgains.databinding.ActivityHomeBinding
import com.example.groupgains.notifications.NotificationsActivity
import com.example.groupgains.ui.create.CreateActivity
import com.example.groupgains.ui.record.RecordActivity
import com.example.groupgains.ui.profile.ProfileActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HomeActivity @Inject constructor(): AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val viewModel: HomeViewModel by viewModels()

    override fun onDestroy() {
        super.onDestroy()


    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        viewModel.initializeActivity(this)

        setContentView(binding.root)

        binding.navView.selectedItemId = R.id.navigation_home
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
                R.id.navigation_notification -> {
                    startActivity(Intent(this, NotificationsActivity::class.java))
                    true
                }
                else -> false
            }
        }

        // Setup the recycler view for viewing posts in feed
        val feedAdapter = FeedAdapter(emptyList(), viewModel)
        val feedRecyclerView = findViewById<RecyclerView>(R.id.feedRecyclerView)
        feedRecyclerView.layoutManager = LinearLayoutManager(this)
        feedRecyclerView.adapter = feedAdapter

        // Setup observer for when posts to display changes
        viewModel.sessionsData.observe(this, { sessions ->
            feedAdapter.feedList = sessions
            feedAdapter.notifyDataSetChanged()
        })

        // Initial load of workout data
        viewModel.loadSessionData()

        val searchView: SearchView = findViewById(R.id.searchView)
        val layoutToHide: ConstraintLayout = findViewById(R.id.layoutToHide)

        val friendAdapter = FriendAdapter(emptyList(), viewModel)
        val friendRecyclerView: RecyclerView = findViewById(R.id.friendRecyclerView)
        friendRecyclerView.layoutManager = LinearLayoutManager(this)
        friendRecyclerView.adapter = friendAdapter

        // Setup observer for when posts to display changes
        viewModel.friends.observe(this, { friends ->
            friendAdapter.friendList = friends
            friendAdapter.notifyDataSetChanged()
        })

        searchView.setOnQueryTextFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                layoutToHide.visibility = View.GONE
                friendRecyclerView.visibility = View.VISIBLE
            } else {
                layoutToHide.visibility = View.VISIBLE
                friendRecyclerView.visibility = View.GONE
            }
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                // You can use this method to perform the search when the user submits the query
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                // This method is called when the query text is changed.
                if (!newText.isNullOrEmpty()) {
                    // If the new text is not empty, hide the layoutToHide and show the friendsRecyclerView
                    layoutToHide.visibility = View.GONE
                    friendRecyclerView.visibility = View.VISIBLE
                    viewModel.loadFriendData(this@HomeActivity, newText)
                } else {
                    // If the new text is empty, show the layoutToHide and hide the friendsRecyclerView
                    layoutToHide.visibility = View.VISIBLE
                    friendRecyclerView.visibility = View.GONE
                }
                return false
            }
        })
    }
}