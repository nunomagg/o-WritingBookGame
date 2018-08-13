package com.nunomagg.network

import com.nunomagg.services.GameManagementService
import com.nunomagg.services.GameManagementServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class UserManagementController {
    @Autowired
    lateinit var gameManagementServiceImpl: GameManagementService

    @PostMapping("user/create")
    fun createUser(@RequestParam(value = "name", required = true) name: String) = gameManagementServiceImpl.createUser(name)

    @GetMapping("user/{userId}")
    fun getUser(@PathVariable(value = "userId", required = true) userId: Long) = gameManagementServiceImpl.getUser(userId)

    @GetMapping("users")
    fun getUsers() = gameManagementServiceImpl.getUsers()

    @GetMapping("user/{userId}/score")
    fun getUserScore(@PathVariable(value = "userId", required = true) userId: Long) = gameManagementServiceImpl.getUserScore(userId)
}