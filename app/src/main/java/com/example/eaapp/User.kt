package com.example.eaapp

class User {
    lateinit var email : String
    lateinit var firstName : String
    lateinit var lastName : String
    lateinit var nickName : String

    constructor(){

    }

    constructor(Email: String, FirstName: String, LastName: String, NickName: String) {
        this.email = Email
        this.firstName = FirstName
        this.lastName = LastName
        this.nickName = NickName
    }

}