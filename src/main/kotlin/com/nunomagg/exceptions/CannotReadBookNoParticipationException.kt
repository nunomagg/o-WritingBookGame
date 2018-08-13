package com.nunomagg.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
class CannotReadBookNoParticipationException(userId : Long, bookId: Long) : Exception(
        "Cannot read book $bookId - The user $userId didn't participate in it"
)