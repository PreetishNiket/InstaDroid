package com.example.instadroid.modelClass

class User{
    var bio:String?=null
    var fullName:String?=null
    var image: String?=null
    var userName:String?=null
    var uid:String?=null
    constructor(){

    }

    constructor(bio: String?, fullName: String?, image: String?, userName: String?, uid: String?) {
        this.bio = bio
        this.fullName = fullName
        this.image = image
        this.userName = userName
        this.uid = uid
    }
}
