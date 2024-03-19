package com.example.groupgains.ui.record

//TODO: Add more data and move this data class eventually
data class Exercise(
        val title: String = "No Name",
        val numSets: Int = 0,
        val sets: MutableList<Set> = mutableListOf<Set>()
)