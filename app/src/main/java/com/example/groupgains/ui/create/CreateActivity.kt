package com.example.groupgains.ui.create

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.groupgains.R
import com.example.groupgains.databinding.ActivityCreateBinding
import com.example.groupgains.databinding.CreateWorkoutListBinding
import com.example.groupgains.home.HomeActivity
import com.example.groupgains.ui.record.CreateOne
import com.example.groupgains.ui.record.RecordActivity
import com.example.groupgains.ui.record.RecordOne
import com.google.android.material.bottomnavigation.BottomNavigationView

class CreateActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCreateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val create1 = CreateOne()

        // Get instance of FragmentManager
        val fragmentManager = supportFragmentManager

        // Begin transaction
        val fragmentTransaction = fragmentManager.beginTransaction()

        fragmentTransaction.add(R.id.frame, create1)

        // Commit transaction
        fragmentTransaction.commit()


        binding.navView.selectedItemId = R.id.navigation_create

        binding.navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
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