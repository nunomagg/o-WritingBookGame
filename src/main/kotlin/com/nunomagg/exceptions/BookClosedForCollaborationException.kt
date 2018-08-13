package com.nunomagg.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
class BookClosedForCollaborationException(bookId: Long) : Exception(
        "Book is closed for collaboration - bookId: $bookId"
)