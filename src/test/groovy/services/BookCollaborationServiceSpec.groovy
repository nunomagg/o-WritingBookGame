package services


import spock.lang.Specification

class BookCollaborationServiceSpec extends Specification {

//    def "should write in book and test the existence of the text and the user in the participants"() {
//        given:
//        BookCollaborationItem book = new BookCollaborationItem(bookSeqId)
//
//        when:
//        book.writeInBook(users[0], lines[0])
//
//        then:
//        book.readBook() == lines[0]
//
//        and:
//        book.getParticipants().contains(users[0])
//    }
//
//    def 'should add multiple users and their lines to a book and check their existence'() {
//        given:
//        BookCollaborationItem book = new BookCollaborationItem(bookSeqId)
//
//        when:
//        users.eachWithIndex { it, idx ->
//            book.writeInBook(it, lines[idx])
//        }
//
//        then:
//        book.readBook().contains(lines.join(" "))
//
//        and:
//        book.getParticipants().containsAll(users)
//    }
//
//    def "should create a book add some lines close the book and not allow another user to add more"(){
//        given:
//        BookCollaborationItem book = new BookCollaborationItem(bookSeqId)
//
//        users.subList(0,3).eachWithIndex{ it,idx -> book.writeInBook(it, lines[idx]) }
//        def participants = book.endCollaboration()
//
//        when:
//        def writeInBookSuccessful = book.writeInBook(users[4], lines[4])
//
//        then:
//        !writeInBookSuccessful
//
//        and:
//        book.readBook().contains(lines.subList(0,3).join(" "))
//
//        and:
//        book.getParticipants().containsAll(users.subList(0,3))
//
//        and:
//        participants == book.getParticipants()
//    }
}
