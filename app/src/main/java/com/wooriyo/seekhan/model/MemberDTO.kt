package com.wooriyo.seekhan.model

data class MemberDTO(
    var status: Int? = 0,
    var msg: String,
    var useridx: String,
    var userid: String,
    var userpass: String,
    var cmpname: String,
    var cmpnum: String,
    var cmpaddr: String,
    var cmptel: String,
    var cmpfax: String,
    var cmpphone: String,
    var name: String,
    var userlv: String
)