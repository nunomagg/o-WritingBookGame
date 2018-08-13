package com.nunomagg.data

data class UserScore(val userId: Long, val score: Int) {
    var username: String = ""

    constructor(userId: Long, username: String, score: Int) : this(userId, score) {
        this.username = username
    }

    override fun toString(): String {
        return "UserScore(userId=$userId, username=$username, score=$score)"
    }
}