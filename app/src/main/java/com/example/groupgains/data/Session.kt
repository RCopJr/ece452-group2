package com.example.groupgains.data

data class Session(
    var user_id: String = "",
    var id: String = "",
    var workoutId: String = "",
    var timestamp: String = "",
    var stats: Stats = Stats()
    //TODO: add other user reactions
)

