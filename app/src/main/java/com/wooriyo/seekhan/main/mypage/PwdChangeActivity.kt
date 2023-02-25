package com.wooriyo.seekhan.main.mypage

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wooriyo.seekhan.MyApplication
import com.wooriyo.seekhan.api.ApiClient
import com.wooriyo.seekhan.databinding.ActivityPwdChangeBinding
import com.wooriyo.seekhan.model.PwdChangeDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PwdChangeActivity : AppCompatActivity() {

    private val TAG = "PwdChange"

    //뷰 바인딩
    lateinit var binding: ActivityPwdChangeBinding

    var useridx = MyApplication.pref.getUserIdx().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPwdChangeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            llBack.setOnClickListener { finish() }
            ivBack.setOnClickListener { finish() }

            btnResult.setOnClickListener {
                val beforePwd = etBeforePwd.text.toString()
                val newPwd = etNewPwd.text.toString()
                val newPwdCheck = etNewPwdCheck.text.toString()

                val pwdPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$ %^&*-]).{8,16}\$"


                if (beforePwd.isEmpty()) {
                    Toast.makeText(this@PwdChangeActivity, "이전 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }else if (newPwd.isEmpty() || newPwdCheck.isEmpty()) {
                    Toast.makeText(this@PwdChangeActivity, "새 비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }else if (!newPwd.matches(pwdPattern.toRegex())) {
                    Toast.makeText(this@PwdChangeActivity, "8~16자리 영문 대소문자, 숫자, 특수문자를 혼합해 주세요.", Toast.LENGTH_SHORT).show()
                }else if (newPwd != newPwdCheck) {
                    Toast.makeText(this@PwdChangeActivity, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                }else if (beforePwd == newPwd) {
                    Toast.makeText(this@PwdChangeActivity, "이전 비밀번호는 사용할 수 없습니다.", Toast.LENGTH_SHORT).show()
                }else {
                    ApiClient.service.udtPwd(beforePwd, newPwd, useridx)
                        .enqueue(object : Callback<PwdChangeDTO> {
                            override fun onResponse(call: Call<PwdChangeDTO>, response: Response<PwdChangeDTO>) {
                                Log.d(TAG, "비밀번호 변경 url => $response")
                                Log.d(TAG, "이전 비밀번호 => $beforePwd // 바꿀 비번 => $newPwd // useridx => $useridx")

                                if (response.isSuccessful) {
                                    when (response.body()?.status) {
                                        1 -> {
                                            Toast.makeText(this@PwdChangeActivity, "비밀번호가 변경 됐습니다.", Toast.LENGTH_SHORT).show()
                                            finish()
                                        }
                                        2 -> Toast.makeText(this@PwdChangeActivity, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                                        3 -> Toast.makeText(this@PwdChangeActivity, response.body()!!.msg, Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }

                            override fun onFailure(call: Call<PwdChangeDTO>, t: Throwable) {
                                Log.d(TAG, t.toString())
                            }
                        })
                }
            }
        }
    }
}