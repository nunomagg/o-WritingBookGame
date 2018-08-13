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
class GameManagementServiceImpl() : IGameManagementService {
    @Autowired
    private lateinit var userManagementService: UserManagementService
    @Autowired
    private lateinit var bookCollaborationService: BookCollaborationService
    @Autowired
    private lateinit var scoringSystemService: ScoringSystemService
    @Autowired
    private lateinit var turnSystemService: TurnSystemService

    constructor(userManagementService: UserManagementService, bookCollaborationService: BookCollaborationService, scoringSystemService: ScoringSystemService, turnSystemService: TurnSystemService) : this() {
        this.bookCollaborationService = bookCollaborationService
        this.userManagementService = userManagementService
        this.scoringSystemService = scoringSystemService
        this.turnSystemService = turnSystemService
    }

    override fun createUser(name: String) = userManagementService.createUser(name)

    override fun getUser(userId: Long): User? = userManagementService.getUser(userId)

    override fun getUsers(): MutableCollection<User> = userManagementService.getUsers()

    override fun getUserScore(userId: Long): Int? = scoringSystemService.getUserScore(userId)

    override fun createBook(): Long = bookCollaborationService.createBook()

    override fun requestBookLine(userId: Long, bookId: Long): String {
        validateUsersExistence(userId)
        validateUserHasntWroteInBook(userId, bookId)

        turnSystemService.startTurn(userId, bookId)
        return bookCollaborationService.participateInBook(userId, bookId)
    }

    @NotYetImplemented
    private fun validateUserHasntWroteInBook(userId: Long, bookId: Long) {
        // if implemented this method should return an exception if the user already participated on the book
        // as to not allow the user to get a new line.
    }

    private fun validateUserParticipationInBook(userId: Long, bookId: Long) {
        if (!bookCollaborationService.hasParticipatedOnBook(userId, bookId)) {
            throw CannotWriteBookException(userId, bookId)
        }
    }

    override fun writeInBook(userId: Long, bookId: Long, line: String): Boolean {
        validateUsersExistence(userId)
        validateUserParticipationInBook(userId, bookId)
        validateUserHasntWroteInBook(userId, bookId)

        turnSystemService.validateUserTurn(userId, bookId)

        val writeInBook = bookCollaborationService.writeInBook(bookId, line)
        if (writeInBook) {
            turnSystemService.endTurn(userId, bookId)
        }

        return writeInBook
    }

    override fun readBook(userId: Long, bookId: Long): String {
        validateUsersExistence(userId)
        val participantOnBook = bookCollaborationService.hasParticipatedOnBook(userId, bookId)

        if (!participantOnBook) {
            throw CannotReadBookNoParticipationException(userId, bookId)
        }

        if (bookCollaborationService.isOpenForCollaboration(bookId)) {
            throw CannotReadBookStillOpenException(bookId)
        }

        return bookCollaborationService.readBook(bookId)
    }

    override fun completeBook(bookId: Long): Boolean {
        val bookWasCompleted = bookCollaborationService.completeBook(bookId)
        if (bookWasCompleted) {
            scoringSystemService.addBookPointToUsers(bookCollaborationService.getBookParticipantsIds(bookId))
        }
        return bookWasCompleted
    }

    override fun getOpenBooks(): Set<Long> = bookCollaborationService.getBooksOpenToCollaboration()

    override fun getLeaderBoard(): List<UserScore> {
        return scoringSystemService.getLeaderBoard().mapNotNull { it ->
            val user = userManagementService.getUser(it.userId)
            if (user != null) {
                it.username = user.name
                it
            } else null
        }
    }

    private fun validateUsersExistence(userId: Long) {
        userManagementService.getUser(userId) ?: throw UserNotFoundException(userId)
    }

}