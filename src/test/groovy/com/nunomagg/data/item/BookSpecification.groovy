package com.nunomagg.data.item


import spock.lang.Specification

class BookSpecification extends Specification {

    def "should create a new book with some text and be able to append to it"(){
        def line1 = "If I had a world of my own"
        def line2 = "everything would be nonsense"
        def id = 1

        given:
        Book book = new Book(id,line1)

        when:
        book.appendText(line2)

        then:
        book.getText() == "$line1 $line2"

        and:
        book.id == id
    }
}
