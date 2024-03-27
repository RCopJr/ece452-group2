package com.example.groupgains

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.groupgains.databinding.ActivityHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    lateinit var auth: FirebaseAuth
    lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()
        val db = FirebaseFirestore.getInstance()
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            goToLogin()
        }
        val currentUser = auth.currentUser
        loadUserData(currentUser!!.uid.toString())
        loadWorkoutData(currentUser.uid.toString())

        binding.btnLogout.setOnClickListener {
            //for logout
            auth.signOut()
            goToLogin()
        }

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d("UserInsertTest", "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w("UserInsertTest", "Error adding document", e)
            }

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home,
                R.id.navigation_record,
                R.id.navigation_create,
                R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController);
    }

    fun goToLogin() {
        startActivity(Intent(this@HomeActivity, MainActivity::class.java))
        finish()
    }

    fun loadUserData(userID: String) {

        var userRef = db.collection("users")
        userRef.whereEqualTo("user_id", userID)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    Toast.makeText(this@HomeActivity, "No User Found", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

            }
    }

    fun loadWorkoutData(userID: String) {

        var workoutsRef = db.collection("workouts")
        workoutsRef.whereEqualTo("user_id", userID)
            .get()
            .addOnSuccessListener {
                if (it.isEmpty) {
                    Toast.makeText(this@HomeActivity, "No User Found", Toast.LENGTH_SHORT).show()
                    return@addOnSuccessListener
                }

            }
    }

}