package com.example.groupgains.ui.record

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
import com.example.groupgains.databinding.ActivityRecordBinding
import com.example.groupgains.home.HomeActivity
import com.example.groupgains.home.HomeViewModel
import com.example.groupgains.notifications.NotificationsActivity
import com.example.groupgains.ui.create.CreateActivity
import com.example.groupgains.ui.profile.ProfileActivity


class RecordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRecordBinding


    private val viewModel: RecordViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initializeActivity(this)

        binding = ActivityRecordBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val record1 = RecordOne()

        // Get instance of FragmentManager
        val fragmentManager = supportFragmentManager

        // Begin transaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.frame, record1)

        // Commit transaction
        fragmentTransaction.commit()

        binding.navView.selectedItemId = R.id.navigation_record

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

//        parentFragmentManager.beginTransaction().apply {
//            replace(R.id.frame, record1)
//            commit()
//        }

    }
}