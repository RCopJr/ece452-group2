package com.example.groupgains.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.groupgains.R
import com.example.groupgains.databinding.ActivityHomeBinding
import com.example.groupgains.databinding.ActivityProfileBinding
import com.example.groupgains.home.HomeActivity
import com.example.groupgains.home.HomeViewModel
import com.example.groupgains.ui.create.CreateActivity
import com.example.groupgains.ui.record.RecordActivity
import android.util.Log

class ProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityProfileBinding

    private val viewModel: ProfileViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.initializeActivity(this)

        binding.btnLogout.setOnClickListener {
            //for logout
            viewModel.signOut(this)
        }

        Log.d("TEST IN PROFILE ONE ONCREATE", "TEST")

        val profile1 = ProfileOne()

        // Get instance of FragmentManager
        val fragmentManager = supportFragmentManager

        // Begin transaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.frame, profile1)

        // Commit transaction
        fragmentTransaction.commit()

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
    }
}