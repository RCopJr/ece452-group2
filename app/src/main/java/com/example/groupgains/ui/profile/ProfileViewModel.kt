package com.example.groupgains.ui.profile

import android.app.Activity
import android.content.Intent
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.groupgains.data.Exercise
import com.example.groupgains.data.Set
import com.example.groupgains.data.Workout
import com.example.groupgains.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import com.example.groupgains.home.FeedViewModelInterface
import com.example.groupgains.data.SessionData
import com.example.groupgains.data.Session
import com.example.groupgains.data.User

class ProfileViewModel : ViewModel(), FeedViewModelInterface {
    override val user_id = MutableLiveData<String>()
    val sessionsData = MutableLiveData<List<SessionData>>()
    val user = MutableLiveData<User>()
    val user_doc_id = MutableLiveData<String>()


    fun loadSessionData(context: Activity) {
        Log.d("gonna load", "PLEASE LOAD SESSION DATA")
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
                                                reactions = session.reactions
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


    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    val workoutsLiveData: MutableLiveData<List<Workout>> = MutableLiveData()


    fun initializeActivity(context: Activity){
        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        if (auth.currentUser == null) {
            goToLogin(context)
        }
        loadWorkoutData(auth.currentUser!!.uid)
        loadUserData(auth.currentUser!!.uid)
    }

    fun signOut(context: Activity){
        auth.signOut()
        goToLogin(context)
    }

    fun loadWorkoutData(userID: String){
        val workoutsCollection = db.collection("workouts")
        workoutsCollection.whereEqualTo("user_id", userID)
            .get()
            .addOnSuccessListener { documents ->
                val workoutsList = mutableListOf<Workout>()
                for (document in documents) {
                    // Handle each document (workout) here
                    val workout = document.toObject<Workout>()
                    for (exercise in workout.exercises) {
                        exercise.numSets = exercise.sets.count()
                    }
                    Log.d("WORKOUT DATA", "$workout")
                    workoutsList.add(workout)
                }
                workoutsLiveData.value = workoutsList
            }
            .addOnFailureListener { exception ->
                // Handle any errors that occurred
                Log.e("Firestore", "Error getting workouts", exception)
            }
    }

    private val _text = MutableLiveData<String>().apply {
        value = "This is the profile Fragment"
    }
    val text: LiveData<String> = _text

    private val mutableSelectedWorkout = MutableLiveData<Workout>()
    val selectedWorkout: LiveData<Workout> get() = mutableSelectedWorkout

    fun selectWorkout(workout: Workout) {
        mutableSelectedWorkout.value = workout
    }

    private fun goToLogin(context: Activity) {
        context.startActivity(Intent(context, LoginActivity::class.java))
        context.finish()
    }

}