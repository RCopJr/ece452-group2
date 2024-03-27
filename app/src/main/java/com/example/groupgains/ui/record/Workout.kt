package com.example.groupgains.ui.record

data class Workout(
    val title: String,
    val exercises: MutableList<Exercise> = mutableListOf<Exercise>(),
)
