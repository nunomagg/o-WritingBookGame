package com.nunomagg.services

import com.nunomagg.data.colaboratorItems.BookCollaborationItem
import com.nunomagg.exceptions.BookClosedForCollaborationException
import com.nunomagg.exceptions.BookNotFoundException
import org.springframework.stereotype.Service

@Service
class BookCollaborationService {
    private val booksById = hashMapOf<Long, BookCollaborationItem>()
    private val openCollaborationBooksIds = mutableSetOf<Long>()
    private var seqId: Long = 0

    fun createBook(): Long {
        val book = BookCollaborationItem(seqId)
        openCollaborationBooksIds.add(seqId)
        booksById[seqId] = book
        return seqId++
    }

    fun participateInBook(userId: Long, bookId: Long): String {
        val book = validateBookOpenForCollaboration(bookId)
        return book.participateInBook(userId)
    }

    fun hasParticipatedOnBook(userId: Long, bookId: Long): Boolean {
        val book = validateBookExistence(bookId)
        return book.participants.contains(userId)
    }

    fun completeBook(bookId: Long): Boolean {
        val book = validateBookExistence(bookId)
        val originalState = book.isOpenToCollaboration()
        if (originalState) {
            openCollaborationBooksIds.remove(bookId)
            book.endCollaboration()
        }
        return originalState
    }

    fun writeInBook(bookId: Long, text: String): Boolean {
        val book = validateBookExistence(bookId)
        return book.writeInBook(text)
    }

    fun readBook(bookId: Long): String {
        val book = validateBookExistence(bookId)
        return book.readBook()
    }

    fun getBookParticipantsIds(bookId: Long): Set<Long> {
        val book = validateBookExistence(bookId)
        return book.participants.toSet()
    }

    fun getBooksOpenToCollaboration() = openCollaborationBooksIds.toSet()

    fun isOpenForCollaboration(bookId: Long): Boolean {
        validateBookExistence(bookId)
        return booksById[bookId]!!.isOpenToCollaboration()
    }

    private fun validateBookExistence(bookId: Long): BookCollaborationItem {
        return booksById.getOrElse(bookId) { throw BookNotFoundException(bookId) }
    }

    private fun validateBookOpenForCollaboration(bookId: Long): BookCollaborationItem {
        val book = validateBookExistence(bookId)
        if (!book.isOpenToCollaboration()) {
            throw BookClosedForCollaborationException(bookId)
        }
        return book
    }
}