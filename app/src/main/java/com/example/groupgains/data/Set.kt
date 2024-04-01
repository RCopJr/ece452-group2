package com.example.groupgains.data

data class Set(
    var title: String = "",
    var weight: String = "",
    var reps: String = "",
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "weight" to weight,
            "reps" to reps
        )
    }
}
