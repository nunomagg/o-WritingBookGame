package com.nunomagg.network

import com.nunomagg.services.GameManagementService
import com.nunomagg.services.GameManagementServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController
class GameController {
    @Autowired
    lateinit var gameManagementService: GameManagementService

    @GetMapping("user/{userId}/book/{bookId}/request")
    fun requestBookLine(@PathVariable(value = "userId", required = true) userId: Long,
                        @PathVariable(value = "bookId", required = true) bookId: Long) = gameManagementService.requestBookLine(userId, bookId)

    @PostMapping("user/{userId}/book/{bookId}/write")
    fun writeInBook(@PathVariable(value = "userId", required = true) userId: Long,
                    @PathVariable(value = "bookId", required = true) bookId: Long,
                    @RequestParam(value = "line", required = true) line: String) = gameManagementService.writeInBook(userId, bookId, line)

    @GetMapping("user/{userId}/book/{bookId}/read")
    fun readBook(@PathVariable(value = "userId", required = true) userId: Long,
                 @PathVariable(value = "bookId", required = true) bookId: Long) = gameManagementService.readBook(userId, bookId)

}