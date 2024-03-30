package com.example.groupgains.data

data class Set(
    var title: String = "",
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "title" to title,
        )
    }
}
