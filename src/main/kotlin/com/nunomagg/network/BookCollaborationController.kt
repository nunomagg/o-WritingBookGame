package com.nunomagg.network

import com.nunomagg.services.GameManagementService
import com.nunomagg.services.GameManagementServiceImpl
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class BookCollaborationController {
    @Autowired
    lateinit var gameManagementServiceImpl: GameManagementService

    @PostMapping("book/create")
    fun createBook() = gameManagementServiceImpl.createBook()

    @PostMapping("book/{bookId}/complete")
    fun completeBook(@PathVariable(value = "bookId", required = true) bookId: Long) = gameManagementServiceImpl.completeBook(bookId)

    @GetMapping("books/open")
    fun getOpenBooks() = gameManagementServiceImpl.getOpenBooks()
}