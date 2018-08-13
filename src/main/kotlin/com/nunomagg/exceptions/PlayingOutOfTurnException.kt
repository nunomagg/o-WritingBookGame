package com.nunomagg.exceptions

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
class PlayingOutOfTurnException(usersTurn: Long, bookId: Long) : Exception(
        "Playing out of turn - it's user: $usersTurn turn on book $bookId"
)