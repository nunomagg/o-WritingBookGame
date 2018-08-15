package com.nunomagg.services

import spock.lang.Shared
import spock.lang.Specification

class ScoringSystemServiceImplSpec extends Specification {
    @Shared
    ScoringSystemServiceImpl pointSystemFactory

    def setup() {
        pointSystemFactory = new ScoringSystemServiceImpl()
    }

    def "should show leader board with users"() {
        when:
        def board = pointSystemFactory.getLeaderBoard()

        then:
        board.size() <= pointSystemFactory.maxUsersOnLeaderBoard

        and:
        board.size() >= 0
    }

    def "should return leader board sorted by the points descendant"() {
        given:
        def user = 1
        def user2 = 2

        pointSystemFactory.addNewBookPointsToUser(user)
        pointSystemFactory.addNewBookPointsToUser(user)
        pointSystemFactory.addNewBookPointsToUser(user2)

        when:
        def board = pointSystemFactory.getLeaderBoard()

        then:
        board[0].userId == user
        board[0].score == pointSystemFactory.pointsPerBook * 2

        and:
        board[1].userId == user2
        board[1].score == pointSystemFactory.pointsPerBook
    }

}
