package com.wooriyo.seekhan.model

data class UserFindDTO(
    var status: Int? = 0,
    var msg: String,
    var cmpname: String,
    var name: String,
    var userid: String,
    var phone: String,
    var useridx: String,
    var pwd: String
)