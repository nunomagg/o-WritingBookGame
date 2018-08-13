package com.nunomagg.services

import com.nunomagg.exceptions.PlayingOutOfTurnException
import org.springframework.stereotype.Service

@Service
class TurnSystemServiceImpl() : TurnSystemService {
    val userTurnByBookId = mutableMapOf<Long, Long?>()

    private var turnSystemActive: Boolean = true

    constructor(turnSystemActive: Boolean) : this() {
        this.turnSystemActive = turnSystemActive
    }

    override fun startTurn(userId: Long, bookId: Long) {
        if (!turnSystemActive) return

        validateUserTurn(userId, bookId)

        userTurnByBookId[userId] = bookId
    }

    override fun endTurn(userId: Long, bookId: Long) {
        if (!turnSystemActive) return

        validateUserTurn(userId, bookId)

        userTurnByBookId[bookId] = null
    }

    override fun isUsersTurn(userId: Long, bookId: Long) = (isFree(bookId) || userTurnByBookId[bookId] == userId) || !turnSystemActive

    override fun validateUserTurn(userId: Long, bookId: Long) {
        val userTurn = userTurnByBookId[bookId]
        if (!isUsersTurn(userId, bookId)) {
            throw PlayingOutOfTurnException(userTurn!!, bookId)
        }
    }

    private fun isFree(bookId: Long) = userTurnByBookId[bookId] == null
}