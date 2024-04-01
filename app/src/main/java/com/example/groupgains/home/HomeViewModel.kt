package com.example.groupgains.home

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
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
import kotlinx.coroutines.tasks.await
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

    fun loadSessionData(context: Activity) {
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
                                                stats = session.stats
                                            )
                                            sessionDataList.add(sessionDataObj)
                                        }
                                        .addOnFailureListener { exception ->
                                            println("Error getting workout: $exception")
                                        }
                                }
                                .addOnFailureListener { exception ->
                                    println("Error getting user: $exception")
                                }
                        }
                        sessionsData.value = sessionDataList
                    }.addOnFailureListener { exception ->
                        println("Error getting sessions: $exception")
                    }
                }
            }
            .addOnFailureListener { exception ->
                println("Error getting friends list: $exception")
            }
    }

    fun updateFriendList(userRef: DocumentReference, friend_id: String, operation: String) {
        val updateOperation = if (operation == "add") FieldValue.arrayUnion(friend_id) else FieldValue.arrayRemove(friend_id)
        userRef.update("friends", updateOperation)
            .addOnSuccessListener {
                Log.d("Update Friend", "Friend ID $operation to/from friends list successfully")
            }
            .addOnFailureListener { e ->
                Log.w("Update Friend", "Error $operation Friend ID to/from friends list", e)
            }
    }

    fun handleFriendClick(friend_id: String) {
        val userRef = db.collection("users").document(user_doc_id.value ?: "")
        val friendRef = db.collection("users").whereEqualTo("user_id", friend_id)

        userRef.get()
            .addOnSuccessListener { document ->
                val friends = document.get("friends") as? List<*>
                val operation = if (friends != null && friend_id in friends) "remove" else "add"
                updateFriendList(userRef, friend_id, operation)

                friendRef.get()
                    .addOnSuccessListener { documents ->
                        for (document in documents) {
                            updateFriendList(document.reference, user_id.value ?: "", operation)
                        }
                    }
            }
    }

    private fun goToLogin(context: Activity) {
        context.startActivity(Intent(context, LoginActivity::class.java))
        context.finish()
    }

}