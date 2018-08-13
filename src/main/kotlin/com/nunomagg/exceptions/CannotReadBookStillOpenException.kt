package com.nunomagg.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
class CannotReadBookStillOpenException(val bookId: Long) : Exception(
        "Cannot read book $bookId - The book is still open for participation"
)