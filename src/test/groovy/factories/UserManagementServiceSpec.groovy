package factories

import com.nunomagg.services.UserManagementService
import spock.lang.Specification

class UserManagementServiceSpec extends Specification {


    def "should create a user and check its existence"(){
        UserManagementService userFactory = new UserManagementService()

        when:
        def user = userFactory.createUser("User A")

        then:
        userFactory.getUser(user) != null
    }

    def "should return null if user doesn't exist"(){
        UserManagementService userFactory = new UserManagementService()

        when:
        userFactory.createUser("User A")

        then:
        userFactory.getUser(10) == null
    }

}
