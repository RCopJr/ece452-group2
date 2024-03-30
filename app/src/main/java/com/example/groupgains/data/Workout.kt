package com.example.groupgains.data

data class Workout(
    var title: String = "",
    val exercises: MutableList<Exercise> = mutableListOf(),
    var id: String = "",
    var ordered_id: String = "",
    var user_id: String = ""
)
