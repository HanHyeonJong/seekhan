package com.wooriyo.seekhan.api

import com.wooriyo.seekhan.model.*
import retrofit2.Call
import retrofit2.http.*

interface Api {

//    //로그인
//    @FormUrlEncoded
//    @POST("/api/check_mbr.php")
//    fun checkMbr(
//        @Field("userid") userid: String,
//        @Field("pass") pass: String,
//        @Field("push_token") push_token: String,
//        @Field("os") os: String,
//        @Field("osvs") osvs: String,
//        @Field("appvs") appvs: String,
//        @Field("md") md: String,
//    ): Call<MemberDTO>

    //로그인
    @GET("/api/check_mbr.php")
    fun checkMbr(
        @Query("userid") userid: String,
        @Query("pass") pass: String,
        @Query("push_token") push_token: String,
        @Query("os") os: String,
        @Query("osvs") osvs: String,
        @Query("appvs") appvs: String,
        @Query("md") md: String,
    ): Call<MemberDTO>

    //비밀번호 변경
    @FormUrlEncoded
    @POST("/api/udtpwd.php")
    fun udtPwd(
        @Field("old_pwd") userid: String,
        @Field("pwd") pass: String,
        @Field("useridx") push_token: String,
    ): Call<PwdChangeDTO>

    //아아디 찾기
    @FormUrlEncoded
    @POST("/api/findid.php")
    fun findId(
        @Field("cmpname") cmpname: String,
        @Field("name") name: String
    ): Call<UserFindDTO>

    //비밀번호 찾기
    @FormUrlEncoded
    @POST("/api/findpwd.php")
    fun findPwd(
        @Field("userid") userid: String,
        @Field("name") name: String,
        @Field("phone") phone: String
    ): Call<UserFindDTO>

    //비밀번호 찾기 후 비밀번호 재설정
    @FormUrlEncoded
    @POST("/api/pwdresult.php")
    fun pwdResult(
        @Field("useridx") useridx: String,
        @Field("pwd") pwd: String
    ): Call<UserFindDTO>

//    //신청내역 (QR History)
//    @FormUrlEncoded
//    @POST("/api/gethistory.php")
//    fun getHistory(
//        @Field("useridx") useridx: String
//    ): Call<GoodsDTO>

    //신청내역 (QR History)
    @GET("/api/gethistory.php")
    fun getHistory(
        @Query("useridx") useridx: String
    ): Call<GoodsDTO>

    //QR Code 조회
    @FormUrlEncoded
    @POST("/api/searchqr.php")
    fun searchQr(
        @Field("qrcode") qrcode: String,
        @Field("useridx") useridx: String
    ): Call<QrScanDTO>

    //상품 진행상태 변경
    @FormUrlEncoded
    @POST("/api/udtmethod.php")
    fun udtMethod(
        @Field("idx") idx: String,
        @Field("useridx") useridx: String,
        @Field("method") method: String,
        @Field("goodstype") goodstype: String
    ): Call<GoodsDTO>
}