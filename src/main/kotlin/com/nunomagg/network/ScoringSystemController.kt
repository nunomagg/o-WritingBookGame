package com.nunomagg.network

import com.nunomagg.services.GameManagementService
import com.nunomagg.services.GameManagementServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ScoringSystemController {
    @Autowired
    lateinit var gameManagementServiceImpl: GameManagementService

    @GetMapping("leaderboard")
    fun getLeaderBoard() = gameManagementServiceImpl.getLeaderBoard()
}