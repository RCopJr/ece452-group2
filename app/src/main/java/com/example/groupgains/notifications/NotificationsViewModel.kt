package com.example.groupgains.notifications

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupgains.data.Session
import com.example.groupgains.data.User
import com.example.groupgains.data.Workout
import com.example.groupgains.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject

class NotificationsViewModel : ViewModel() {
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    val friendRequestsLiveData: MutableLiveData<List<User>> = MutableLiveData()
    val user_doc_id = MutableLiveData<String>()
    val user_id = MutableLiveData<String>()

    fun initializeActivity(context: Activity){
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()
        if (auth.currentUser == null) {
            goToLogin(context)
        }

        loadUserData(auth.currentUser!!.uid)
    }

    private fun loadUserData(userID: String){
        user_id.postValue(userID)
        val userRef = db.collection("users")
        userRef.whereEqualTo("user_id", userID)
            .get()
            .addOnSuccessListener { documents ->
                val userDocument = documents.documents.firstOrNull()
                if (userDocument != null) {
                    Log.d("Load User", "${userDocument.id} => ${userDocument.data}")
                    user_doc_id.postValue(userDocument.id)
                } else {
                    Log.d("Load User", "No document found for userID: $userID")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Load User", "Error getting documents: ", exception)
            }
    }

    fun loadFriendRequestData(){
        user_id.value = auth.currentUser!!.uid
        val userRef = db.collection("users")
        userRef.whereEqualTo("user_id", user_id.value)
            .get()
            .addOnSuccessListener { documents ->
                val userDocument = documents.documents.firstOrNull()
                if (userDocument != null) {
                    Log.d("Load User", "${userDocument.id} => ${userDocument.data}")
                    user_doc_id.postValue(userDocument.id)
                    val userData = userDocument.toObject(User::class.java)
                    Log.d("RETRIEVED USER", "$userData")

                    if (userData != null && userData.friendRequests.isNotEmpty()) {
                        Log.d("FRIEND REQUESTS NOT EMPTY", "${userData.friendRequests}")
                        db.collection("users")
                            .whereIn("user_id", userData.friendRequests)
                            .get()
                            .addOnSuccessListener { friendDocs ->
                                val friendRequestList = mutableListOf<User>()
                                for (friendDoc in friendDocs) {
                                    val user = friendDoc.toObject<User>()
                                    friendRequestList.add(user)
                                }
                                friendRequestsLiveData.value = friendRequestList
                                Log.d("FRIEND REQUEST LIVE DATA", "${friendRequestsLiveData.value}")
                            }
                            .addOnFailureListener { exception ->
                                Log.w("Load User", "Error getting documents: ", exception)
                            }
                    }
                } else {
                    Log.d("Load User", "No document found for userID: ${user_id.value}")
                }
            }
            .addOnFailureListener { exception ->
                Log.w("Load User", "Error getting documents: ", exception)
            }
    }

    fun updateFriendList(userRef: DocumentReference, friend_id: String) {
        userRef.update("friends", FieldValue.arrayUnion(friend_id))
            .addOnSuccessListener {
                Log.d("Update Friend", "Friend ID add to/from friends list successfully")
                userRef.update("friendRequests", FieldValue.arrayRemove(friend_id))
            }
            .addOnFailureListener { e ->
                Log.w("Update Friend", "Error add Friend ID to/from friends list", e)
            }
    }

    fun handleFriendClick(friend_id: String) {
        val userRef = db.collection("users").document(user_doc_id.value ?: "")
        val friendRef = db.collection("users").whereEqualTo("user_id", friend_id)

        userRef.get()
            .addOnSuccessListener { document ->
                val friends = document.get("friends") as? List<*>
                updateFriendList(userRef, friend_id)

                friendRef.get()
                    .addOnSuccessListener { documentsAdd ->
                        for (document in documentsAdd) {
                            updateFriendList(document.reference, user_id.value ?: "")
                        }
                    }
            }
    }

    private fun goToLogin(context: Activity) {
        context.startActivity(Intent(context, LoginActivity::class.java))
        context.finish()
    }

}