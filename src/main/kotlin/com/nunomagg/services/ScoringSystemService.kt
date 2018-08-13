package com.nunomagg.services

import com.nunomagg.data.UserScore

interface ScoringSystemService {
    fun getFullLeaderBoard(): List<UserScore>
    fun getLeaderBoard(): List<UserScore>
    fun addNewBookPointsToUser(userId: Long)
    fun addBookPointToUsers(userId: Collection<Long> )
    fun getUserScore(userId: Long): Int
}