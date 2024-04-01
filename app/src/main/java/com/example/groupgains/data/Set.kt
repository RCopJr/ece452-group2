package com.example.groupgains.data

data class Set(
    var title: String = "",
    var weight: String = "",
    var reps: String = "",
    var checked: Boolean = false
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
            "weight" to weight,
            "reps" to reps,
            "checked" to false
        )
    }
}
