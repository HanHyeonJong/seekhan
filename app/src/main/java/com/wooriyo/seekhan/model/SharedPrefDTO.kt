package com.wooriyo.seekhan.model

import android.content.Context
import android.content.SharedPreferences

class SharedPrefDTO(context: Context) {
    private  val pref : SharedPreferences = context.getSharedPreferences("seekhan_Pref_key", Context.MODE_PRIVATE)

    //토큰 담기
    fun setToken(push_token: String) {
        pref.edit().putString("push_token", push_token).apply()
    }

    //토큰 가져오기
    fun getToken(): String? {
        return pref.getString("push_token", "")
    }

    //유저 담기
    fun setUser(osvs: String, appvs: String, md: String, useridx: String, userid: String, userpass: String,cmpname: String, cmpnum:String, cmpaddr: String, cmptel: String, cmpfax: String, cmpphone: String, name: String, userlv: String) {
        pref.edit().putString("osvs", osvs).apply()
        pref.edit().putString("appvs", appvs).apply()
        pref.edit().putString("md", md).apply()
        pref.edit().putString("useridx", useridx).apply()
        pref.edit().putString("userid", userid).apply()
        pref.edit().putString("userpass", userpass).apply()
        pref.edit().putString("cmpname", cmpname).apply()
        pref.edit().putString("cmpnum", cmpnum).apply()
        pref.edit().putString("cmpaddr", cmpaddr).apply()
        pref.edit().putString("cmptel", cmptel).apply()
        pref.edit().putString("cmpfax", cmpfax).apply()
        pref.edit().putString("cmpphone", cmpphone).apply()
        pref.edit().putString("name", name).apply()
        pref.edit().putString("userlv", userlv).apply()
    }

    //userlv 가져오기
    fun getuserlv(): String? {
        return pref.getString("userlv", "")
    }

    //name 가져오기
    fun getname(): String? {
        return pref.getString("name", "")
    }

    //pass 가져오기
    fun getuserpass(): String? {
        return pref.getString("userpass", "")
    }

    //cmpphone 가져오기
    fun getcmpphone(): String? {
        return pref.getString("cmpphone", "")
    }

    //cmpfax 가져오기
    fun getcmpfax(): String? {
        return pref.getString("cmpfax", "")
    }

    //cmptel 가져오기
    fun getcmptel(): String? {
        return pref.getString("cmptel", "")
    }

    //cmpaddr 가져오기
    fun getcmpaddr(): String? {
        return pref.getString("cmpaddr", "")
    }

    //cmpnum 가져오기
    fun getcmpnum(): String? {
        return pref.getString("cmpnum", "")
    }

    //cmpName 가져오기
    fun getCmpName(): String? {
        return pref.getString("cmpname", "")
    }

    //userId 가져오기
    fun getUserId(): String? {
        return pref.getString("userid", "")
    }

    //os버전 가져오기
    fun getOsvs(): String? {
        return pref.getString("osvs", "")
    }

    //앱버전 가져오기
    fun getAppvs(): String? {
        return pref.getString("appvs", "")
    }

    //단말기종 가져오기
    fun getMd(): String? {
        return pref.getString("md", "")
    }

    //유저 idx 담기
    fun setUserIdx(useridx: String) {
        pref.edit().putString("useridx", useridx).apply()
    }

    //단말기종 가져오기
    fun getUserIdx(): String? {
        return pref.getString("useridx", "")
    }

}