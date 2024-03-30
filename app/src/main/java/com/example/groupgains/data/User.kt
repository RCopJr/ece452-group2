package com.example.groupgains.data

data class User(
    val first: String = "",
    val last : String = "",
    val friends: MutableList<String> = mutableListOf()
)
