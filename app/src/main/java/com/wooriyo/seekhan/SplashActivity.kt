package com.wooriyo.seekhan

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wooriyo.seekhan.api.ApiClient
import com.wooriyo.seekhan.databinding.ActivitySplashBinding
import com.wooriyo.seekhan.login.LoginActivity
import com.wooriyo.seekhan.main.MainActivity
import com.wooriyo.seekhan.model.MemberDTO
import com.wooriyo.seekhan.util.Encoder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SplashActivity : AppCompatActivity() {

    private val TAG = "Splash"

    //뷰 바인딩
    lateinit var binding: ActivitySplashBinding

    //고객의 정보 받아오기
    val osvs = Build.VERSION.SDK_INT.toString() //접속 단말 os 버전
    val appvs = BuildConfig.VERSION_NAME // 접속 단말 설치 앱 버전
    val md = Build.MODEL//접속 단말 모델명
    var push_token = ""
    val os = "A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //상단바 투명하게
        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        splash()

    }

    private fun splash() {
        Handler(Looper.getMainLooper()).postDelayed({

            autoLogin()

        }, 500)
    }

    private fun autoLogin() {
        val prefId = MyApplication.pref.getUserId().toString()
        val prefPass = MyApplication.pref.getuserpass().toString()

        push_token = MyApplication.pref.getToken().toString()

        Log.d(TAG, "로그인 => $prefId")
        if (prefId.isEmpty()) {
            Log.d(TAG, "로그인 화면으로 이동")
            val intent = Intent(this@SplashActivity, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }else {
            Log.d(TAG, "자동 로그인")
            ApiClient.service.checkMbr(Encoder.urlEncode(prefId).toString(), Encoder.urlEncode(prefPass).toString(), push_token, os, osvs, appvs, md)
                .enqueue(object : Callback<MemberDTO> {
                    override fun onResponse(call: Call<MemberDTO>, response: Response<MemberDTO>) {
                        Log.d(TAG, "로그인 API url => $response")
                        Log.d(TAG, "로그인 API status => ${response.body()?.status}")
                        Log.d(TAG, "id => $prefId // pwd => $prefPass // 토큰 => $push_token // osvs => $osvs // appvs => $appvs // md => $md // os => $os // 유저레벨 => ${response.body()?.userlv}")


                        if (response.isSuccessful) {
                            when(response.body()?.status) {
                                1 -> {
                                    //내부저장
                                    MyApplication.pref.setUser(osvs, appvs, md, response.body()!!.useridx, prefId, prefPass, response.body()!!.cmpname, response.body()!!.cmpnum, response.body()!!.cmpaddr, response.body()!!.cmptel, response.body()!!.cmpfax, response.body()!!.cmpphone, response.body()!!.name, response.body()!!.userlv)

                                    val intent = Intent(this@SplashActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                2 -> {
                                    Toast.makeText(this@SplashActivity, response.body()!!.msg, Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                4 -> {
                                    Toast.makeText(this@SplashActivity, response.body()!!.msg, Toast.LENGTH_SHORT).show()

                                    val intent = Intent(this@SplashActivity, LoginActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<MemberDTO>, t: Throwable) {
                        Log.d(TAG, t.toString())
                        Toast.makeText(this@SplashActivity, "다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}