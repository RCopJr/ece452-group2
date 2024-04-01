package com.example.groupgains.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.groupgains.R
import com.example.groupgains.databinding.ActivityHomeBinding
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)

        viewModel.initializeActivity(this)

        binding.btnLogout.setOnClickListener {
            //for logout
            viewModel.signOut(this)
        }

        //binding = ActivityHomeBinding.inflate(layoutInflater)
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
                else -> false
            }
        }
    }

    fun loadWorkoutData(workoutID: String) {
        viewModel.loadWorkoutData(workoutID, this)
    }

}