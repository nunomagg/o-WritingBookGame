package com.nunomagg.services

import spock.lang.Shared
import spock.lang.Specification

class UserManagementServiceImplSpec extends Specification {

    @Shared
    def userFactory
    def setup(){
        userFactory = new UserManagementServiceImpl()
    }

    def "should create a user and check its existence"(){
        when:
        def user = userFactory.createUser("User A")

        then:
        userFactory.getUser(user) != null
    }

    def "should return null if user doesn't exist"(){
        when:
        userFactory.createUser("User A")

        then:
        userFactory.getUser(10) == null
    }

}
