package com.wooriyo.seekhan.login

import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wooriyo.seekhan.BuildConfig
import com.wooriyo.seekhan.MyApplication
import com.wooriyo.seekhan.api.ApiClient
import com.wooriyo.seekhan.databinding.ActivityLoginBinding
import com.wooriyo.seekhan.main.MainActivity
import com.wooriyo.seekhan.model.MemberDTO
import com.wooriyo.seekhan.util.Encoder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity(), OnClickListener {

    private val TAG = "Login"

    //뷰 바인딩
    lateinit var binding: ActivityLoginBinding

    //고객의 정보 받아오기
    val osvs = Build.VERSION.SDK_INT.toString() //접속 단말 os 버전
    val appvs = BuildConfig.VERSION_NAME // 접속 단말 설치 앱 버전
    val md = Build.MODEL//접속 단말 모델명
    var push_token = ""
    val os = "A"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //상단바 투명하게
        window.apply {
            decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            statusBarColor = Color.TRANSPARENT
        }

        binding.run {
            login.setOnClickListener(this@LoginActivity)
            tvUserFind.setOnClickListener(this@LoginActivity)
            tvTaol.setOnClickListener(this@LoginActivity)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.login.id -> {
                loginAPI()
            }

            binding.tvUserFind.id -> {
                val intent = Intent(this@LoginActivity, UserFindActivity::class.java)
                startActivity(intent)
                finish()
            }

            binding.tvTaol.id -> {
                val intent = Intent(this@LoginActivity, WebViewActivity::class.java)
                startActivity(intent)
            }
        }
    }

    //로그인 API
    private fun loginAPI() {
        val userid = binding.etId.text.toString()
        val pass = binding.etPwd.text.toString()

        push_token = MyApplication.pref.getToken().toString()

        val useridEncoder = Encoder.urlEncode(userid)

        Log.d(TAG, "인코딩 전??? => $userid")
        Log.d(TAG, "인코딩 됨??? => ${Encoder.urlEncode(userid)}")
        Log.d(TAG, "base64 됨??? => ${Encoder.base64Encode(userid)}")
        Log.d(TAG, "인코딩 후 base64 됨??? => ${Encoder.base64Encode(useridEncoder.toString())}")

        if (userid.isEmpty()) {
            Toast.makeText(this@LoginActivity, "아이디(이메일)를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        } else if (pass.isEmpty()) {
            Toast.makeText(this@LoginActivity, "비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
            return
        } else {
            ApiClient.service.checkMbr(
                Encoder.urlEncode(userid).toString(),
                Encoder.urlEncode(pass).toString(),
                push_token,
                os,
                osvs,
                appvs,
                md
            )
                .enqueue(object : Callback<MemberDTO> {
                    override fun onResponse(call: Call<MemberDTO>, response: Response<MemberDTO>) {
                        Log.d(TAG, "로그인 API url => $response")
                        Log.d(TAG, "로그인 API status => ${response.body()?.status}")
                        Log.d(TAG, "id => $userid // pwd => $pass // 토큰 => $push_token // osvs => $osvs // appvs => $appvs // md => $md // os => $os // 유저레벨 => ${response.body()?.userlv}")

                        if (response.isSuccessful) {
                            when (response.body()?.status) {
                                1 -> {
                                    //내부저장
                                    MyApplication.pref.setUser(osvs, appvs, md, response.body()!!.useridx, userid, pass, response.body()!!.cmpname, response.body()!!.cmpnum, response.body()!!.cmpaddr, response.body()!!.cmptel, response.body()!!.cmpfax, response.body()!!.cmpphone, response.body()!!.name, response.body()!!.userlv)

                                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                                    startActivity(intent)
                                    finish()
                                }

                                2 -> Toast.makeText(this@LoginActivity, response.body()!!.msg, Toast.LENGTH_SHORT).show()

                                4 -> Toast.makeText(this@LoginActivity, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<MemberDTO>, t: Throwable) {
                        Log.d(TAG, t.toString())
                        Toast.makeText(this@LoginActivity, "다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                    }
                })
        }
    }
}