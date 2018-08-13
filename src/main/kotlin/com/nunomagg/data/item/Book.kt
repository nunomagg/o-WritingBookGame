package com.nunomagg.data.item

class Book(id: Long) : AbstractItem(id) {
    private var textBuilder: MutableList<String>

    init {
        this.textBuilder = mutableListOf()
    }

    constructor(id: Long, text: String) : this(id) {
        this.textBuilder = mutableListOf(text)
    }

    fun getText(): String {
        return textBuilder.joinToString(" ")
    }

    fun appendText(text: String) {
        if (text.isNotBlank()) {
            textBuilder.add(text)
        }
    }

    fun getLastLine() : String{
        return if (!textBuilder.isEmpty()) textBuilder.last() else ""
    }

    override fun toString(): String {
        return "Book(id=$id, text=${getText()})"
    }
}