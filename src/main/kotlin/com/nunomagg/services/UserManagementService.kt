package com.nunomagg.services

import com.nunomagg.data.User

interface UserManagementService {
    fun createUser(name: String): Long
    fun getUser(id: Long): User?
    fun getUsers(): MutableCollection<User>
}