package com.wooriyo.seekhan.model

data class PwdChangeDTO(
    var status: Int? = 0,
    var msg: String,
    var old_pwd: String,
    var pwd: String,
    var useridx: String
)