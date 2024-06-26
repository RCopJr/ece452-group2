package com.example.groupgains.home

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import com.example.groupgains.data.Reactions
import com.example.groupgains.data.User

import com.example.groupgains.data.Session
import com.example.groupgains.data.SessionData
import com.example.groupgains.data.Workout
import com.example.groupgains.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(): ViewModel() {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    val sessionsData = MutableLiveData<List<SessionData>>()
    val friends = MutableLiveData<List<User>>()
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
        loadUserData(auth.currentUser!!.uid)
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

    fun signOut(context: Activity){
        auth.signOut()
        goToLogin(context)
    }

    fun loadFriendData(context: Activity, textFilter: String) {
        val usersRef = db.collection("users")
        usersRef
            .orderBy("userName")
            .whereGreaterThanOrEqualTo("userName", textFilter)
            .whereLessThan("userName", textFilter + "\uf8ff")
            .get()
            .addOnSuccessListener { documents ->
                val friends = documents.mapNotNull { it.toObject(User::class.java) }
                this.friends.postValue(friends)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(context, "Error getting friends: $exception", Toast.LENGTH_SHORT).show()
            }
    }

    fun loadSessionData() {
        db.collection("users").whereEqualTo("user_id", auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { userDocument ->

                val userData = userDocument.documents[0].toObject<User>()
                val friendsList = userData?.friends as List<*>
                Log.d("FRIENDS LIST", "$friendsList")

                if (friendsList.isNotEmpty()) {
                    val sessionsQuery = db.collection("sessions")
                        .whereIn("user_id", friendsList)

                    sessionsQuery.get().addOnSuccessListener { sessionDocuments ->
                        val sessionsList = mutableListOf<Session>()
                        for (document in sessionDocuments) {
                            val session = document.toObject<Session>()
                            sessionsList.add(session)
                        }

                        val sessionDataList = mutableListOf<SessionData>()
                        for (session in sessionsList) {
                            // Query the users collection with the user_id from the session
                            db.collection("users").whereEqualTo("user_id", session.user_id)
                                .get()
                                .addOnSuccessListener { userDocuments ->
                                    val user = userDocuments.documents[0].toObject<User>()

                                    // Query the workouts collection with the workoutId from the session
                                    db.collection("workouts").document(session.workoutId ?: "")
                                        .get()
                                        .addOnSuccessListener { documentSnapshot ->
                                            val workout = documentSnapshot.toObject(Workout::class.java)
                                            val sessionDataObj = SessionData(
                                                userName = user?.userName ?: "",
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
                                .addOnFailureListener { exception ->
                                    println("Error getting user: $exception")
                                }
                        }
                    }.addOnFailureListener { exception ->
                        println("Error getting sessions: $exception")
                    }
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting friends list: $exception")
            }
    }

    fun removeFromFriendList(userRef: DocumentReference, friend_id: String) {
        userRef.update("friends", FieldValue.arrayRemove(friend_id))
            .addOnSuccessListener {
                Log.d("Update Friend", "Friend ID removed to/from friends list successfully")
            }
            .addOnFailureListener { e ->
                Log.w("Update Friend", "Error removed Friend ID to/from friends list", e)
            }
    }

    fun handleFriendClick(friend_id: String) {
        val friendRef = db.collection("users").whereEqualTo("user_id", friend_id)
        val userRef = db.collection("users").document(user_doc_id.value ?: "")

        userRef.get()
            .addOnSuccessListener { document ->
                friendRef.get()
                    .addOnSuccessListener { dfocuments ->
                        val friends = document.get("friends") as? List<*>
                        if (friends != null && friends.contains(friend_id)) {
                            removeFromFriendList(userRef, friend_id)
                            val friendDocument = dfocuments.documents.firstOrNull()
                            if (friendDocument != null) {
                                removeFromFriendList(db.collection("users").document(friendDocument.id), user_id.value.toString())
                            }
                        } else {
                            val friendDocument = dfocuments.documents.firstOrNull()
                            if (friendDocument != null && friendDocument.exists()) {
                                // Retrieve the user object
                                val user = friendDocument.toObject(User::class.java)
                                user?.friendRequests?.add(user_id.value.toString())
                                db.collection("users").document(friendDocument.id).set(user!!)
                                    .addOnSuccessListener {
                                    }
                                    .addOnFailureListener { exception ->
                                    }
                            }
                        }
                    }
                .addOnFailureListener { exception ->
                }
            }
    }

    private fun goToLogin(context: Activity) {
        context.startActivity(Intent(context, LoginActivity::class.java))
        context.finish()
    }

    fun updateReactionsInDb(reactions: Reactions, session_id: String) {
        db.collection("sessions")
            .document(session_id)
            .update(mapOf("reactions" to reactions))
            .addOnSuccessListener {}
            .addOnFailureListener { e ->
                // Error handling
                println("Error adding workout: $e")
            }
    }
}
