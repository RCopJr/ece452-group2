package com.example.groupgains.data

data class Workout(
    val title: String = "",
    val exercises: MutableList<Exercise> = mutableListOf(),
)
