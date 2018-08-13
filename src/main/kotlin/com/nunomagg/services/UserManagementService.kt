package com.nunomagg.services

import com.nunomagg.data.User
import org.springframework.stereotype.Service

@Service
class UserManagementService {
    private val usersById = hashMapOf<Long, User>()
    private var seqId : Long = 0

    fun createUser(name: String): Long {
        val user = User(seqId,name)
        usersById[seqId] = user
        return seqId++
    }

    fun getUser(id: Long) = usersById[id]

    //TODO: set immutable?
    fun getUsers() = usersById.values
}