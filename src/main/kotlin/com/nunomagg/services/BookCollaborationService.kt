package com.nunomagg.services

interface BookCollaborationService {

    fun createBook(): Long
    fun participateInBook(userId: Long, bookId: Long): String
    fun hasParticipatedOnBook(userId: Long, bookId: Long): Boolean
    fun completeBook(bookId: Long): Boolean
    fun writeInBook(bookId: Long, text: String): Boolean
    fun readBook(bookId: Long): String
    fun getBookParticipantsIds(bookId: Long): Set<Long>
    fun getBooksOpenToCollaboration(): Set<Long>
    fun isOpenForCollaboration(bookId: Long): Boolean
}