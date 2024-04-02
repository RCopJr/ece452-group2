package com.example.groupgains.data

data class SessionData(
    var userName: String = "",
    var userProfilePicture: String = "",
    var workoutName: String = "",
    var timestamp: String = "",
    var stats: Stats = Stats(),
    //TODO: add other user reactions
    var reactions: Reactions = Reactions(),
)

