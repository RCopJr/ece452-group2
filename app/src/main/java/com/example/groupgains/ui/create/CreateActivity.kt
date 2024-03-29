package com.example.groupgains.ui.create

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.groupgains.R
import com.example.groupgains.databinding.ActivityCreateBinding
import com.example.groupgains.databinding.CreateWorkoutListBinding
import com.example.groupgains.home.HomeActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class CreateActivity: AppCompatActivity() {

    private lateinit var binding: ActivityCreateBinding
    private val viewModel: CreateViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.navView.selectedItemId = R.id.navigation_create

        binding.navView.setOnItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                else -> false
            }
        }

        val contain: LinearLayout = binding.workoutsContainer

        addList(21, contain, layoutInflater)

        binding.buttonNew.setOnClickListener {
            viewModel.goToCreateTwo(this)
        }

    }

    private fun addList(data: Int, container: LinearLayout, inflater: LayoutInflater) {

        for (i in 1..data){
            val binding = CreateWorkoutListBinding.inflate(inflater, container, false)
            binding.setTitle.text = "Workout $i"
            binding.setTitle.textSize = 20.toFloat()
            container.addView(binding.root)

            val button = binding.editButton

            button.setOnClickListener {
                viewModel.goToCreateTwo(this)
            }

        }
    }
}