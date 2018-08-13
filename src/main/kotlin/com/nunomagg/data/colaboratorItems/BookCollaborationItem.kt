package com.nunomagg.data.colaboratorItems

import com.nunomagg.data.item.Book

class BookCollaborationItem(id: Long) : AbstractCollaborationItem<Book, Long>() {
    override val item: Book = Book(id)

    fun writeInBook(text: String): Boolean {
        if (!text.isBlank() && openCollaboration){
            item.appendText(text)
            return true
        }
        return false
    }

    fun readBook() : String{
        return item.getText()
    }

    /**
     * Adds the user as a participant in the gameManagementService and returns the last line of it
     * */
    fun participateInBook(userId: Long): String {
        addParticipant(userId)
        return getLastLine()
    }

    fun getLastLine() : String{
        return item.getLastLine()
    }
}