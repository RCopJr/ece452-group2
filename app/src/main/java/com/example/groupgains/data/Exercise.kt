package com.example.groupgains.data

//TODO: Add more data and move this data class eventually
data class Exercise(
        val title: String = "No Name",
        var numSets: Int = 0,
        val sets: MutableList<Set> = mutableListOf()
)