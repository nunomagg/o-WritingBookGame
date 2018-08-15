package com.nunomagg.services

import com.nunomagg.exceptions.PlayingOutOfTurnException
import spock.lang.Shared
import spock.lang.Specification

class TurnSystemServiceImplSpec extends Specification {
    @Shared
    def turnSystemService

    def setup() {
        turnSystemService = new TurnSystemServiceImpl(true)
    }

    def "should allow to start turn"() {
        when:
        turnSystemService.startTurn(1, 1)

        then:
        noExceptionThrown()
    }

    def "should allow to start turn when its another users turn"() {
        given:
        turnSystemService.startTurn(1, 1)

        when:
        turnSystemService.startTurn(2, 1)

        then:
        thrown(PlayingOutOfTurnException)
    }

    def "should allow a user to end  another users turn"() {
        given:
        turnSystemService.startTurn(1, 1)

        when:
        turnSystemService.endTurn(2, 1)

        then:
        thrown(PlayingOutOfTurnException)
    }

    def "should allow to alternate turns"() {
        given:
        turnSystemService.startTurn(1, 1)
        turnSystemService.endTurn(1, 1)
        turnSystemService.startTurn(2, 1)

        when:
        turnSystemService.endTurn(2, 1)

        then:
        noExceptionThrown()
    }
}
