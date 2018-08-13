package com.nunomagg.services

import com.nunomagg.data.User
import com.nunomagg.data.UserScore

interface IGameManagementService {

    fun createUser(name: String) : Long
    fun getUser(userId: Long): User?
    fun getUsers() : MutableCollection<User>
    fun getUserScore(userId: Long): Int?

    fun createBook(): Long
    fun requestBookLine(userId : Long, bookId: Long) : String
    fun writeInBook(userId : Long, bookId: Long, line: String) : Boolean
    fun readBook(userId: Long, bookId: Long) : String
    fun completeBook(bookId: Long) : Boolean

    fun getOpenBooks() : Set<Long>
    fun getLeaderBoard(): List<UserScore>
}