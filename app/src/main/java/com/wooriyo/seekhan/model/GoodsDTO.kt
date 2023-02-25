package com.wooriyo.seekhan.model

data class GoodsDTO(
    var status: Int = 0,
    var msg: String,
    var hlist: ArrayList<HListDTO>,
)

data class HListDTO(
    var idx: String,
    var pidx: String,
    var title: String,
    var method: String,
    var hscode: String,
    var goods_status: String,
    var savemethod: String,
    var weight: String,
    var cbm: String,
    var goodstype: String,
    var cmpname: String,
    var regdt: String
)