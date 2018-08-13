package com.nunomagg.services

import com.nunomagg.data.UserScore
import org.springframework.stereotype.Service

@Service
class ScoringSystemServiceImpl() : ScoringSystemService{
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

    override fun getFullLeaderBoard(): List<UserScore> {
        return pointsByUserId
                .toList()
                .sortedByDescending { (_, value) -> value }
                .map { (userId, score) ->
                    UserScore(userId,score)
                }
    }

    override fun getLeaderBoard(): List<UserScore> = getFullLeaderBoard().take(maxUsersOnLeaderBoard)

    override fun getUserScore(userId: Long) = pointsByUserId[userId] ?: 0

    override fun addNewBookPointsToUser(userId: Long) {
        pointsByUserId[userId]?.let {
            pointsByUserId[userId] = it.plus(pointsPerBook)
        } ?: run {
            pointsByUserId[userId] = pointsPerBook
        }
    }

    override fun addBookPointToUsers(userId: Collection<Long> ){
        userId.onEach { addNewBookPointsToUser(it) }
    }
}
