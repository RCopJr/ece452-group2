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
import android.text.TextWatcher
import android.text.Editable
import android.view.View

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val viewModel: ProfileViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        viewModel.initializeActivity(this)

        setContentView(binding.root)

        //get the username
        viewModel.user.observe(this, { user -> 
            val userName = user.userName
            Log.d("Load UserName", "${userName}")
            Log.d("Load UserName model", "${viewModel}")
            Log.d("Load UserName model", "${viewModel.user}")
            Log.d("Load UserName model value", "${viewModel.user.value}")
            binding.textEdit1.setText(userName);
        })
        
        binding.textEdit1.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                viewModel.updateNameInDb(s.toString())
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
                //
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                //
            }
        })

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