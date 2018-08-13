package com.nunomagg.services

import com.nunomagg.data.UserScore
import org.springframework.stereotype.Service

@Service
class ScoringSystemService() {

    companion object {
        const val DEFAULT_POINTS_PER_BOOK = 10
        const val DEFAULT_MAX_USERS_ON_LEADER_BOARD = 5
    }

    private var pointsPerBook: Int = DEFAULT_POINTS_PER_BOOK
    private var maxUsersOnLeaderBoard: Int = DEFAULT_MAX_USERS_ON_LEADER_BOARD
    private val pointsByUserId = mutableMapOf<Long, Int>()

    constructor(pointsPerBook: Int, maxUsersOnLeaderBoard: Int) : this() {
        this.pointsPerBook = pointsPerBook
        this.maxUsersOnLeaderBoard = maxUsersOnLeaderBoard
    }

    fun getFullLeaderBoard(): List<UserScore> {
        return pointsByUserId
                .toList()
                .sortedByDescending { (_, value) -> value }
                .map { (userId, score) ->
                    UserScore(userId,score)
                }
    }

    fun getLeaderBoard(): List<UserScore> = getFullLeaderBoard().take(maxUsersOnLeaderBoard)

    fun getUserScore(userId: Long) = pointsByUserId[userId] ?: 0

    fun addNewBookPoints(userId: Long) {
        pointsByUserId[userId]?.let {
            pointsByUserId[userId] = it.plus(pointsPerBook)
        } ?: run {
            pointsByUserId[userId] = pointsPerBook
        }
    }

    fun addBookPointToUsers(userId: Collection<Long> ){
        userId.onEach { addNewBookPoints(it) }
    }

}