package com.nunomagg.services

import com.nunomagg.data.User
import com.nunomagg.data.UserScore
import com.nunomagg.exceptions.CannotReadBookNoParticipationException
import com.nunomagg.exceptions.CannotReadBookStillOpenException
import com.nunomagg.exceptions.CannotWriteBookException
import com.nunomagg.exceptions.UserNotFoundException
import groovy.transform.NotYetImplemented
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GameManagementServiceImpl() : GameManagementService {
    @Autowired
    private lateinit var userManagementServiceImpl: UserManagementServiceImpl
    @Autowired
    private lateinit var bookCollaborationServiceImpl: BookCollaborationServiceImpl
    @Autowired
    private lateinit var scoringSystemServiceImpl: ScoringSystemServiceImpl
    @Autowired
    private lateinit var turnSystemServiceImpl: TurnSystemServiceImpl

    constructor(userManagementServiceImpl: UserManagementServiceImpl, bookCollaborationServiceImpl: BookCollaborationServiceImpl, scoringSystemServiceImpl: ScoringSystemServiceImpl, turnSystemServiceImpl: TurnSystemServiceImpl) : this() {
        this.bookCollaborationServiceImpl = bookCollaborationServiceImpl
        this.userManagementServiceImpl = userManagementServiceImpl
        this.scoringSystemServiceImpl = scoringSystemServiceImpl
        this.turnSystemServiceImpl = turnSystemServiceImpl
    }

    override fun createUser(name: String) = userManagementServiceImpl.createUser(name)

    override fun getUser(userId: Long): User? = userManagementServiceImpl.getUser(userId)

    override fun getUsers(): MutableCollection<User> = userManagementServiceImpl.getUsers()

    override fun getUserScore(userId: Long): Int? = scoringSystemServiceImpl.getUserScore(userId)

    override fun createBook(): Long = bookCollaborationServiceImpl.createBook()

    override fun requestBookLine(userId: Long, bookId: Long): String {
        validateUsersExistence(userId)
        validateUserHasntWroteInBook(userId, bookId)

        turnSystemServiceImpl.startTurn(userId, bookId)
        return bookCollaborationServiceImpl.participateInBook(userId, bookId)
    }

    @NotYetImplemented
    private fun validateUserHasntWroteInBook(userId: Long, bookId: Long) {
        // if implemented this method should return an exception if the user already participated on the book
        // as to not allow the user to get a new line.
    }

    private fun validateUserParticipationInBook(userId: Long, bookId: Long) {
        if (!bookCollaborationServiceImpl.hasParticipatedOnBook(userId, bookId)) {
            throw CannotWriteBookException(userId, bookId)
        }
    }

    override fun writeInBook(userId: Long, bookId: Long, line: String): Boolean {
        validateUsersExistence(userId)
        validateUserParticipationInBook(userId, bookId)
        validateUserHasntWroteInBook(userId, bookId)

        turnSystemServiceImpl.validateUserTurn(userId, bookId)

        val writeInBook = bookCollaborationServiceImpl.writeInBook(bookId, line)
        if (writeInBook) {
            turnSystemServiceImpl.endTurn(userId, bookId)
        }

        return writeInBook
    }

    override fun readBook(userId: Long, bookId: Long): String {
        validateUsersExistence(userId)
        val participantOnBook = bookCollaborationServiceImpl.hasParticipatedOnBook(userId, bookId)

        if (!participantOnBook) {
            throw CannotReadBookNoParticipationException(userId, bookId)
        }

        if (bookCollaborationServiceImpl.isOpenForCollaboration(bookId)) {
            throw CannotReadBookStillOpenException(bookId)
        }

        return bookCollaborationServiceImpl.readBook(bookId)
    }

    override fun completeBook(bookId: Long): Boolean {
        val bookWasCompleted = bookCollaborationServiceImpl.completeBook(bookId)
        if (bookWasCompleted) {
            scoringSystemServiceImpl.addBookPointToUsers(bookCollaborationServiceImpl.getBookParticipantsIds(bookId))
        }
        return bookWasCompleted
    }

    override fun getOpenBooks(): Set<Long> = bookCollaborationServiceImpl.getBooksOpenToCollaboration()

    override fun getLeaderBoard(): List<UserScore> {
        return scoringSystemServiceImpl.getLeaderBoard().mapNotNull { it ->
            val user = userManagementServiceImpl.getUser(it.userId)
            if (user != null) {
                it.username = user.name
                it
            } else null
        }
    }

    private fun validateUsersExistence(userId: Long) {
        userManagementServiceImpl.getUser(userId) ?: throw UserNotFoundException(userId)
    }

}