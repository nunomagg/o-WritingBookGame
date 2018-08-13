package com.nunomagg.services

import com.nunomagg.exceptions.PlayingOutOfTurnException
import spock.lang.Specification

class TurnSystemServiceSpec extends Specification {

    def "should allow to start turn"() {
        given:
        def turnSystemService = new TurnSystemService(true)

        when:
        turnSystemService.startTurn(1, 1)

        then:
        noExceptionThrown()
    }

    def "should allow to start turn when its another users turn"() {
        given:
        def turnSystemService = new TurnSystemService(true)
        turnSystemService.startTurn(1, 1)

        when:
        turnSystemService.startTurn(2, 1)

        then:
        thrown(PlayingOutOfTurnException)
    }

    def "should allow a user to end  another users turn"() {
        given:
        def turnSystemService = new TurnSystemService(true)
        turnSystemService.startTurn(1, 1)

        when:
        turnSystemService.endTurn(2, 1)

        then:
        thrown(PlayingOutOfTurnException)
    }

    def "should allow to alternate turns"() {
        given:
        def turnSystemService = new TurnSystemService(true)
        turnSystemService.startTurn(1, 1)
        turnSystemService.endTurn(1, 1)
        turnSystemService.startTurn(2, 1)

        when:
        turnSystemService.endTurn(2, 1)

        then:
        noExceptionThrown()
    }
}
