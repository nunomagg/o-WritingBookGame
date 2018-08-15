package com.nunomagg.data.colaboratorItems

import com.nunomagg.data.User
import spock.lang.Shared
import spock.lang.Specification

class BookCollaborationItemSpec extends Specification {
    @Shared def userSeqId = 0
    @Shared def bookSeqId = 0
    @Shared List<Long> users
    @Shared List<String> lines

    BookCollaborationItem book

    def setupSpec() {
        users = [
                new User(userSeqId++, 'User A').getId(),
                new User(userSeqId++, 'User B').getId(),
                new User(userSeqId++, 'User C').getId(),
                new User(userSeqId++, 'User D').getId(),
                new User(userSeqId++, 'User E').getId()
        ]

        lines = [
                "If I had a world of my own",
                "everything would be nonsense",
                "Nothing would be what it is",
                "because everything would be what it isn’t",
                "Alice."
        ]
    }

    def setup(){
        book = new BookCollaborationItem(bookSeqId)
    }

    def "should not write in book if text is empty"(){
        when:
        def writeInBookSuccessful = book.writeInBook("   ")

        then:
        !writeInBookSuccessful
    }

    def "should not be able to add participant if book is close to collaboration "(){
        given:
        book.endCollaboration()

        when:
        def participantAddedSuccessfully = book.addParticipant(users[0])

        then:
        !participantAddedSuccessfully
    }

    def "should test that a book when created is open to collaboration"(){
        when:
        BookCollaborationItem newBook = new BookCollaborationItem(bookSeqId)

        then:
        newBook.isOpenToCollaboration()
    }
}
