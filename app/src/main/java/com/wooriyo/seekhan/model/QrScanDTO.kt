package com.wooriyo.seekhan.model

data class QrScanDTO(
    var status: Int = 0,
    var msg: String,
    var qrcode: String,
    var idx: String,
    var title: String,
    var weight: String,
    var method: String,
    var cmpname: String,
    var hscode: String,
    var cbm: String,
    var cmb: String,
    var goods_status: String,
    var goodstype: String,
    var regdt: String,
    var savemethod: String
)