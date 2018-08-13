package factories

import com.nunomagg.services.BookCollaborationService
import spock.lang.Specification

class BookCollaborationServiceSpec extends Specification {

    def 'should create a new book and get it'(){
        given:
        BookCollaborationService factory = new BookCollaborationService()

        when:
        factory.createBook()

        then:
        noExceptionThrown()
    }

    def "should be able to create a book and write in it"(){
        given:
        BookCollaborationService factory = new BookCollaborationService()
        def line = "If I had a world of my own"
        def bookId = factory.createBook()

        when:
        def bookWrittenSuccessfully = factory.writeInBook(bookId, line)

        then:
        bookWrittenSuccessfully
    }

    def "should create a new book add a line see that the book contains it"(){
        given:
        BookCollaborationService factory = new BookCollaborationService()
        def line = "If I had a world of my own"
        def bookId = factory.createBook()
        factory.writeInBook(bookId, line)

        when:
        def bookText = factory.readBook(bookId)

        then:
        bookText == line
    }

    def "should create 3 new books and check their existence and that they're open to collaboration"(){
        given:
        BookCollaborationService factory = new BookCollaborationService()

        def book = factory.createBook()
        def book2 = factory.createBook()
        def book3 = factory.createBook()

        when:
        def getBooksCollaborationItems = factory.getBooksOpenToCollaboration()

        then:
        getBooksCollaborationItems.containsAll([book, book2, book3])
    }

    def "should create two books and check only one is open"(){
        given:
        BookCollaborationService factory = new BookCollaborationService()
        def book = factory.createBook()
        def line1 = "If I had a world of my own"
        factory.writeInBook(book, line1)

        def book2 = factory.createBook()
        factory.completeBook(book)

        when:
        def booksOpenToCollaboration = factory.getBooksOpenToCollaboration()

        then:
        !booksOpenToCollaboration.contains(book)

        and:
        booksOpenToCollaboration.contains(book2)
    }

    def "should test that after writing multiple lines the book contains them all"(){
        given:
        BookCollaborationService factory = new BookCollaborationService()
        def bookId = factory.createBook()
        def line1 = "If I had a world of my own"
        def line2 = "Line 2"
        def line3 = "Line 3"

        factory.writeInBook(bookId, line1)
        factory.writeInBook(bookId, line2)
        factory.writeInBook(bookId, line3)

        when:
        def bookText = factory.readBook(bookId)

        then:
        bookText == [line1,line2, line3].join(" ")
    }
}
