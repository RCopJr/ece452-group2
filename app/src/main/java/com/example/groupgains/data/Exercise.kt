package com.example.groupgains.data

//TODO: Add more data and move this data class eventually
data class Exercise(
        var title: String = "",
        var numSets: Int = 0,
        val sets: MutableList<Set> = mutableListOf()
) {
        fun toMap(): Map<String, Any> {
                return mapOf(
                        "title" to title,
                        "sets" to sets,
                )
        }
}