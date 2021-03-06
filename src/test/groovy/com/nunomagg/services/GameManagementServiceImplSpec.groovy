package com.nunomagg.services

import com.nunomagg.exceptions.*
import spock.lang.Shared
import spock.lang.Specification

class GameManagementServiceImplSpec extends Specification {
    @Shared
    List<String> userNames
    @Shared
    List<String> lines

    GameManagementService gameManagementService

    def setup(){
        gameManagementService = new GameManagementServiceImpl(new UserManagementServiceImpl(), new BookCollaborationServiceImpl(), new ScoringSystemServiceImpl(), new TurnSystemServiceImpl())
    }

    def setupSpec() {
        userNames = [
                'User A',
                'User B',
                'User C',
                'User D',
                'User E'
        ]

        lines = [
                "If I had a world of my own",
                "everything would be nonsense",
                "Nothing would be what it is",
                "because everything would be what it isn’t",
                "Alice."
        ]
    }

    def "should test that create user works"(){
        when:
        gameManagementService.createUser(userNames.first())

        then:
        noExceptionThrown()
    }

    def "should test that its possible to get a user after it's creation"(){
        given:
        def userId = gameManagementService.createUser(userNames.first())

        when:
        def user = gameManagementService.getUser(userId)

        then:
        noExceptionThrown()

        and:
        user !=null
    }

    def "should test that when no points were added to a user the user's points are 0"(){
        given:
        def userId = gameManagementService.createUser(userNames.first())

        when:
        def score = gameManagementService.getUserScore(userId)

        then:
        score == 0
    }

    def "should test that that no error is return on userPoints function even if the user doenst exist"(){
        when:
        def score = gameManagementService.getUserScore(20)

        then:
        score == 0

        and:
        noExceptionThrown()
    }

    def "should throw an exception if user tries to read a book if he didnt participate in it"(){
        given:
        def userId = gameManagementService.createUser(userNames.first())
        def bookId = gameManagementService.createBook()

        when:
        gameManagementService.readBook(userId, bookId)

        then:
        thrown(CannotReadBookNoParticipationException)
    }

    def "should validate that a non existent user cannot request a line from a book"(){
        when:
        gameManagementService.requestBookLine(1, 1)

        then:
        thrown(UserNotFoundException)
    }
    def "should validate that a existent user cannot request a line from non existant book"(){
        given:
        def userId = gameManagementService.createUser(userNames.first())

        when:
        gameManagementService.readBook(userId, 2)

        then:
        thrown(BookNotFoundException)
    }


    def "should throw an exception if user tries to read a book without it being completed"(){
        given:
        def userId = gameManagementService.createUser(userNames.first())
        def bookId = gameManagementService.createBook()
        gameManagementService.requestBookLine(userId,bookId)

        when:
        gameManagementService.readBook(userId, bookId)

        then:
        thrown(CannotReadBookStillOpenException)
    }

    def "should test that its possible to create various users and get them all"(){
        given:
        def userId = gameManagementService.createUser(userNames.first())
        def userId2 = gameManagementService.createUser(userNames[1])
        def userId3 = gameManagementService.createUser(userNames[2])
        def userIds = []

        when:
        def users = gameManagementService.getUsers()

        then:
        noExceptionThrown()

        and:

        users.each {
            userIds.add(it.id)
        }
        userIds.containsAll([userId,userId2,userId3])
    }

    def "should test that create collaboration book works without issues"(){
        when:
        gameManagementService.createBook()

        then:
        noExceptionThrown()
    }

    def "should test that a once a book is completed is no longer open for collaboration"(){
        given:
        def bookId = gameManagementService.createBook()

        when:
        gameManagementService.completeBook(bookId)

        then:
        !gameManagementService.openBooks.contains(bookId)
    }

    def "should test that existent user can write in a existent book"(){
        given:
        def userId = gameManagementService.createUser(userNames.first())
        def bookId = gameManagementService.createBook()

        gameManagementService.requestBookLine(userId, bookId)

        when:
        def writeInBookSuccessfully = gameManagementService.writeInBook(userId, bookId, lines.first())

        then:
        writeInBookSuccessfully
    }

    def "should test that existent user cant write in a existent book if not requested first"() {
        given:
        def userId = gameManagementService.createUser(userNames.first())
        def bookId = gameManagementService.createBook()

        when:
        gameManagementService.writeInBook(userId, bookId, lines.first())

        then:
        thrown(CannotWriteBookException)
    }

    def "should test that the a user cannot write to book if the book is completed"(){
        given:
        def bookId = gameManagementService.createBook()
        def userId = gameManagementService.createUser(userNames.first())
        gameManagementService.completeBook(bookId)

        when:
        gameManagementService.writeInBook(userId, bookId, lines.first())

        then:
        thrown(Exception)
    }

    def "should test that users that collaborated on a book can read the book"(){
        given:
        def userIds = new ArrayList<Long>()

        userNames.each {
            userIds.add(gameManagementService.createUser(it))
        }

        def bookId = gameManagementService.createBook()

        userIds.eachWithIndex { it, idx ->
            gameManagementService.requestBookLine(it, bookId)
            gameManagementService.writeInBook(it, bookId,lines[idx])
        }

        when:
        gameManagementService.completeBook(bookId)

        then:
        userIds.each {
            gameManagementService.readBook(it,bookId) == lines.join(" ")
        }

    }

    def "should run project's full happy path"(){
        given:
        def usersIds = new ArrayList<Long>()
        def leaderBoardUsers = new ArrayList<Long>()

        userNames.each {
            usersIds.add(gameManagementService.createUser(it))
        }

        def bookId = gameManagementService.createBook()

        usersIds.eachWithIndex { it, idx ->
            gameManagementService.requestBookLine(it,bookId)
            gameManagementService.writeInBook(it, bookId,lines[idx])
        }

        when:
        gameManagementService.completeBook(bookId)

        then:
        usersIds.each {
            gameManagementService.readBook(it,bookId) == lines.join(" ")
        }

        when:
        gameManagementService.getLeaderBoard().each {
            leaderBoardUsers.add(it.userId)
        }

        then:
        leaderBoardUsers.containsAll(leaderBoardUsers)
    }

    def "should create a book add some participants, complete it and not allow more participants to be added"(){
        given:
        def usersIds = [
                gameManagementService.createUser(userNames[0]),
                gameManagementService.createUser(userNames[1]),
                gameManagementService.createUser(userNames[2])
        ]
        def lastUser = gameManagementService.createUser(userNames[3])
        def bookId = gameManagementService.createBook()

        usersIds.eachWithIndex { it, idx ->
            gameManagementService.requestBookLine(it, bookId)
            gameManagementService.writeInBook(it,bookId,lines[idx])
        }

        gameManagementService.completeBook(bookId)

        when:
        gameManagementService.requestBookLine(lastUser, bookId)

        then:
        thrown(BookClosedForCollaborationException)
    }

    def "should get an out of turn exception if user tries to request a book out of turn"() {
        given:
        def usersIds = [
                gameManagementService.createUser(userNames[0]),
                gameManagementService.createUser(userNames[1]),
                gameManagementService.createUser(userNames[2])
        ]
        def bookId = gameManagementService.createBook()
        gameManagementService.requestBookLine(usersIds[0], bookId)

        when:
        gameManagementService.requestBookLine(usersIds[1], bookId)

        then:
        thrown(PlayingOutOfTurnException)
    }
}
