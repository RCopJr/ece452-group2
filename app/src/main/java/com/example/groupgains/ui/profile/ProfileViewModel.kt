package com.example.groupgains.ui.profile

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupgains.data.Exercise
import com.example.groupgains.data.Session
import com.example.groupgains.data.SessionData
import com.example.groupgains.data.Set
import com.example.groupgains.data.User
import com.example.groupgains.data.Workout
import com.example.groupgains.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.example.groupgains.data.User

class ProfileViewModel : ViewModel() {

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    val workoutsLiveData: MutableLiveData<List<Workout>> = MutableLiveData()
    
    val sessionsData = MutableLiveData<List<SessionData>>()
    val user = MutableLiveData<User>()
    val user_id = MutableLiveData<String>()
    val user_doc_id = MutableLiveData<String>()

    fun initializeActivity(context: Activity){
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        if (auth.currentUser == null) {
            goToLogin(context)
        }
        val userId = auth.currentUser?.uid
        if (userId != null) {
            loadWorkoutData(userId)
            Log.d("TEST IN VIEW MODAL", "TEST")
        }
        loadUserData(auth.currentUser!!.uid)
    }

    fun loadUserData(userID: String){
        // user_id.postValue(userID)
        val userRef = db.collection("users")
        userRef.whereEqualTo("user_id", userID)
        .get()
        .addOnSuccessListener { documents ->
            val userDocument = documents.documents.firstOrNull()
            if (userDocument != null) {
                Log.d("Load User", "${userDocument.id} => ${userDocument.data}")
                val userData = userDocument.toObject(User::class.java)
                user.postValue(userData)
            } else {
                Log.d("Load User", "No document found for userID: $userID")
            }
        }
        .addOnFailureListener { exception ->
            Log.w("Load User", "Error getting documents: ", exception)
        }
    }

    public fun updateNameInDb(userName: String) {
        println("about to update username in db ${userName}")
        val userId = auth.currentUser?.uid
        if (userId != null) {
            db.collection("users")
            .whereEqualTo("user_id", userId)
            .get()
            .addOnSuccessListener {
                documents ->
                val userDocument = documents.documents.firstOrNull()

                val id = userDocument!!.id;
                db.collection("users").document(id).update(mapOf(
                    "userName" to userName
                ))
                // Document updated successfully
                // You can handle success here if needed
                println("update success")
            }
            .addOnFailureListener { e ->
                // Error handling
                println("Error updating username: $e")
            }
        }
    }

    fun signOut(context: Activity){
        auth.signOut()
        goToLogin(context)
    }

    fun loadUserData(userID: String){
        user_id.postValue(userID)
        val userRef = db.collection("users")
        userRef.whereEqualTo("user_id", userID)
            .get()
            .addOnSuccessListener { documents ->
                val userDocument = documents.documents.firstOrNull()
                if (userDocument != null) {
                    Log.d("Load User", "${userDocument.id} => ${userDocument.data}")
                    val userData = userDocument.toObject(User::class.java)
                    user.postValue(userData)
                    user_doc_id.postValue(userDocument.id)
                  
                } else {
                    Log.d("Load User", "No document found for userID: $userID")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Load User", "Error getting documents: ", exception)
            }
    }

    private fun goToLogin(context: Activity) {
        context.startActivity(Intent(context, LoginActivity::class.java))
        context.finish()
    }

    fun loadSessionData(context: Activity) {
        loadUserData(auth.currentUser!!.uid)

        val sessionsQuery = db.collection("sessions")
            .whereEqualTo("user_id", auth.currentUser!!.uid)

        sessionsQuery.get().addOnSuccessListener { sessionDocuments ->
            val sessionsList = mutableListOf<Session>()
            for (document in sessionDocuments) {
                val session = document.toObject<Session>()
                sessionsList.add(session)
            }

            val sessionDataList = mutableListOf<SessionData>()
            for (session in sessionsList) {
                // Query the users collection with the user_id from the session

                // Query the workouts collection with the workoutId from the session
                db.collection("workouts").document(session.workoutId)
                    .get()
                    .addOnSuccessListener { documentSnapshot ->
                        val workout = documentSnapshot.toObject(Workout::class.java)
                        val sessionDataObj = SessionData(
                            userName = user.value?.userName ?: "",
                            userProfilePicture = "",
                            workoutName = workout?.title ?: "",
                            timestamp = session.timestamp,
                            stats = session.stats,
                            reactions = session.reactions,
                            sessionId = session.id
                        )
                        sessionDataList.add(sessionDataObj)
                        sessionsData.value = sessionDataList
                    }
                    .addOnFailureListener { exception ->
                        println("Error getting workout: $exception")
                    }
            }

        }
    }
}