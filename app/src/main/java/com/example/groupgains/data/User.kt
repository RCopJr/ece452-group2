package com.example.groupgains.data

data class User(
    val userName: String = "",
    val user_id: String = "",
    val friends: MutableList<String> = mutableListOf(),
    val color: Int = 2131165357, //Default is green *2131165357*
)
