package com.example.groupgains.ui.profile

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.groupgains.R
import com.example.groupgains.databinding.ActivityProfileBinding
import com.example.groupgains.home.HomeActivity
import com.example.groupgains.ui.create.CreateActivity
import com.example.groupgains.ui.record.RecordActivity
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        viewModel.initializeActivity(this)

        setContentView(binding.root)

        binding.btnLogout.setOnClickListener {
            //for logout
            viewModel.signOut(this)
        }

        binding.navView.selectedItemId = R.id.navigation_profile

        binding.navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.navigation_create -> {
                    startActivity(Intent(this, CreateActivity::class.java))
                    true
                }
                R.id.navigation_record -> {
                    startActivity(Intent(this, RecordActivity::class.java))
                    true
                }
                else -> false
            }
        }

        val feedAdapter = ProfileFeedAdapter(emptyList(), viewModel)
        val feedRecyclerView = findViewById<RecyclerView>(R.id.recycler)
        feedRecyclerView.layoutManager = LinearLayoutManager(this)
        feedRecyclerView.adapter = feedAdapter

//      Setup observer for when posts to display changes
        viewModel.sessionsData.observe(this, { sessions ->
            feedAdapter.feedList = sessions
            feedAdapter.notifyDataSetChanged()
        })

        viewModel.loadSessionData(this)

    }
}