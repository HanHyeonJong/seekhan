package com.wooriyo.seekhan.login.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.Toast
import com.wooriyo.seekhan.MyApplication
import com.wooriyo.seekhan.api.ApiClient
import com.wooriyo.seekhan.databinding.FragmentPwdBinding
import com.wooriyo.seekhan.login.LoginActivity
import com.wooriyo.seekhan.model.UserFindDTO
import com.wooriyo.seekhan.util.Encoder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PwdFragment : Fragment(), OnClickListener {

    private val TAG = "PwdFragment"

    //뷰 바인딩
    lateinit var binding: FragmentPwdBinding

    lateinit var useridx: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentPwdBinding.inflate(layoutInflater)

        binding.run {
            btnSave.setOnClickListener(this@PwdFragment)
        }

        return binding.root
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.btnSave.id -> {
                val id = binding.etId.text.toString()
                val manager = binding.etManager.text.toString()
                val number = binding.etNumber.text.toString()

                if (id.isEmpty()) {
                    Toast.makeText(context, "아이디를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }else if (manager.isEmpty()) {
                    Toast.makeText(context, "담당자 이름을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }else if (number.isEmpty()) {
                    Toast.makeText(context, "휴대폰 번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }else {
                    //비밀번호 찾기
                    ApiClient.service.findPwd(Encoder.urlEncode(id).toString(), Encoder.urlEncode(manager).toString(), Encoder.urlEncode(number).toString())
                        .enqueue(object : Callback<UserFindDTO> {
                            override fun onResponse(call: Call<UserFindDTO>, response: Response<UserFindDTO>) {
                                Log.d(TAG, "비밀번호 변경 url => $response")
                                Log.d(TAG, "비밀번호 변경 status => ${response.body()?.status}")

                                if (response.isSuccessful) {
                                    when (response.body()?.status) {
                                        1 -> {
                                            binding.run {
                                                tvResultId.text = response.body()!!.userid
                                                llPwdFind.visibility = View.GONE
                                                llResult.visibility = View.VISIBLE

                                                val newPwd = etNewPwd.text.toString()
                                                val newPwdCheck = etNewPwdCheck.text.toString()

                                                useridx = response.body()!!.useridx

                                                val pwdPattern = "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@\$ %^&*-]).{8,16}\$"

                                                if (newPwd.isEmpty() || newPwdCheck.isEmpty()) {
                                                    Toast.makeText(context, "새비밀번호를 입력해 주세요.", Toast.LENGTH_SHORT).show()
                                                }else if (!newPwd.matches(pwdPattern.toRegex())) {
                                                    Toast.makeText(context, "8~16자리 영문 대소문자, 숫자, 특수문자를 혼합해 주세요.", Toast.LENGTH_SHORT).show()
                                                }else if (newPwd != newPwdCheck) {
                                                    Toast.makeText(context, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
                                                }else {
                                                    //비밀번호 찾기 결과
                                                    ApiClient.service.pwdResult(useridx, Encoder.urlEncode(newPwd).toString())
                                                        .enqueue(object : Callback<UserFindDTO> {
                                                            override fun onResponse(call: Call<UserFindDTO>, response: Response<UserFindDTO>) {
                                                                Log.d(TAG, "비밀번호 변경 결과 url => $response")
                                                                Log.d(TAG, "비밀번호 변경 결과 status => ${response.body()?.status}")
                                                                Log.d(TAG, "비밀번호 변경 결과 useridx => $useridx // pwd => $newPwd")

                                                                if (response.isSuccessful) {
                                                                    if (response.body()?.status == 1) {
                                                                        Toast.makeText(context, "비밀번호가 재설정 되었습니다.", Toast.LENGTH_SHORT).show()
//                                                                        activity?.finish()

                                                                        val intent = Intent(context, LoginActivity::class.java)
                                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                                                        startActivity(intent)
                                                                    }
                                                                }
                                                            }

                                                            override fun onFailure(call: Call<UserFindDTO>, t: Throwable) {
                                                                Log.d(TAG, t.toString())
                                                                Toast.makeText(context, "다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                                                            }
                                                        })
                                                }
                                            }
                                        }

                                        2 -> { Toast.makeText(context, response.body()!!.msg, Toast.LENGTH_SHORT).show() }
                                    }
                                }
                            }

                            override fun onFailure(call: Call<UserFindDTO>, t: Throwable) {
                                Log.d(TAG, t.toString())
                                Toast.makeText(context, "다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                            }
                        })
                }
            }
        }
    }
}