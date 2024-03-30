package com.example.groupgains.data

data class Workout(
    var title: String = "",
    val exercises: MutableList<Exercise> = mutableListOf(),
    var id: String = "",
    var ordered_id: String = "",
    var user_id: String = ""
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "exercises" to exercises,
            "id" to id,
            "ordered_id" to ordered_id,
            "user_id" to user_id
            // Map other properties similarly
        )
    }
}
