package com.example.myapplication.Domain

class Location {
    var Id = 0
    var Loc: String? = null
    override fun toString(): String {
        return Loc!!
    }
}