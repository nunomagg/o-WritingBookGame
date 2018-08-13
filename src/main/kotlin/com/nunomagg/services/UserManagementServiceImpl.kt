package com.nunomagg.services

import com.nunomagg.data.User
import org.springframework.stereotype.Service

@Service
class UserManagementServiceImpl : UserManagementService{
    private val usersById = hashMapOf<Long, User>()
    private var seqId : Long = 0

    override fun createUser(name: String): Long {
        val user = User(seqId,name)
        usersById[seqId] = user
        return seqId++
    }

    override fun getUser(id: Long) = usersById[id]

    override fun getUsers() = usersById.values
}