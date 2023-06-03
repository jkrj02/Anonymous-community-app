package com.example.anonymous_community_app.data

data class User(
    var userId:Int=0,
    var gender:Boolean=true,
    var userName:String="",
    var password:String="",
    var isAdmin:Boolean=false){
    constructor(userName: String, password: String) : this() {
        this.userName = userName
        this.password = password
    }
}
