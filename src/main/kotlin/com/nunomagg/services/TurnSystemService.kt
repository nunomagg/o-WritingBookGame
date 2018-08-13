package com.nunomagg.services

interface TurnSystemService {
    fun startTurn(userId: Long, bookId: Long)
    fun endTurn(userId: Long, bookId: Long)
    fun isUsersTurn(userId: Long, bookId: Long): Boolean
    fun validateUserTurn(userId: Long, bookId: Long)
}