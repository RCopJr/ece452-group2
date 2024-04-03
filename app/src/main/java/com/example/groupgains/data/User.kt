package com.example.groupgains.data

data class User(
    val userName: String = "",
    val user_id: String = "",
    val friends: MutableList<String> = mutableListOf(),
    val friendRequests: MutableList<String> = mutableListOf()
)
