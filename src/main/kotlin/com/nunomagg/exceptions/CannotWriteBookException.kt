package com.nunomagg.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
class CannotWriteBookException(userId: Long, bookId: Long) : Exception(
        "Cannot write in book $bookId: user $userId is not a participant. Request the book first"
)
