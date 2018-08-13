package com.nunomagg.data

data class User(val id: Long, val name:String) {

    override fun toString(): String {
        return "User(id=$id, name='$name')"
    }
}