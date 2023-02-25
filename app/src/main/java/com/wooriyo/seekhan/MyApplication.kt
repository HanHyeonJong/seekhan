package com.wooriyo.seekhan

import android.app.Application
import com.wooriyo.seekhan.model.SharedPrefDTO

class MyApplication: Application() {
    companion object {
        lateinit var pref: SharedPrefDTO
    }

    override fun onCreate() {
        pref = SharedPrefDTO(applicationContext)
        super.onCreate()
    }
}