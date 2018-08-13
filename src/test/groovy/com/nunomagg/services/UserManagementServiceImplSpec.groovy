package com.nunomagg.services

import spock.lang.Specification

class UserManagementServiceImplSpec extends Specification {


    def "should create a user and check its existence"(){
        UserManagementServiceImpl userFactory = new UserManagementServiceImpl()

        when:
        def user = userFactory.createUser("User A")

        then:
        userFactory.getUser(user) != null
    }

    def "should return null if user doesn't exist"(){
        UserManagementServiceImpl userFactory = new UserManagementServiceImpl()

        when:
        userFactory.createUser("User A")

        then:
        userFactory.getUser(10) == null
    }

}
