package factories

import com.nunomagg.services.ScoringSystemService
import spock.lang.Specification

class ScoringSystemFactorySpec extends Specification {

    def "should show leader board with users"(){
        ScoringSystemService pointSystemFactory = new ScoringSystemService()

        when:
        def board = pointSystemFactory.getLeaderBoard()

        then:
        board.size() <= pointSystemFactory.maxUsersOnLeaderBoard

        and:
        board.size() >= 0
    }

    def "should return leader board sorted by the points descendant"(){
        ScoringSystemService pointSystemFactory = new ScoringSystemService()
        def user = 1
        def user2 = 2

        pointSystemFactory.addNewBookPoints(user)
        pointSystemFactory.addNewBookPoints(user)
        pointSystemFactory.addNewBookPoints(user2)

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
