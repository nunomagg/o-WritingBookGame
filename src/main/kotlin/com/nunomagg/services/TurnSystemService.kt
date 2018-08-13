package com.nunomagg.services

import com.nunomagg.exceptions.PlayingOutOfTurnException
import org.springframework.stereotype.Service

@Service
class TurnSystemService() {
    val userTurnByBookId = mutableMapOf<Long, Long?>()

    private var turnSystemActive: Boolean = true

    constructor(turnSystemActive: Boolean) : this() {
        this.turnSystemActive = turnSystemActive
    }

    fun startTurn(userId: Long, bookId: Long) {
        if (!turnSystemActive) return

        validateUserTurn(userId, bookId)

        userTurnByBookId[userId] = bookId
    }

    fun endTurn(userId: Long, bookId: Long) {
        if (!turnSystemActive) return

        validateUserTurn(userId, bookId)

        userTurnByBookId[bookId] = null
    }

    fun isUsersTurn(userId: Long, bookId: Long) = (isFree(bookId) || userTurnByBookId[bookId] == userId) || !turnSystemActive

    fun validateUserTurn(userId: Long, bookId: Long) {
        val userTurn = userTurnByBookId[bookId]
        if (!isUsersTurn(userId, bookId)) {
            throw PlayingOutOfTurnException(userTurn!!, bookId)
        }
    }


    private fun isFree(bookId: Long) = userTurnByBookId[bookId] == null
}