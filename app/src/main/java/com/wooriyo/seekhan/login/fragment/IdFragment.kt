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
import com.wooriyo.seekhan.api.ApiClient
import com.wooriyo.seekhan.databinding.FragmentIdBinding
import com.wooriyo.seekhan.login.LoginActivity
import com.wooriyo.seekhan.model.UserFindDTO
import com.wooriyo.seekhan.util.Encoder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IdFragment : Fragment(), OnClickListener {

    private val TAG = "IdFragment"

    //뷰 바인딩
    lateinit var binding: FragmentIdBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentIdBinding.inflate(layoutInflater)

        binding.run {
            btnSave.setOnClickListener(this@IdFragment)
        }

        return binding.root
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.btnSave.id -> {
                val companyName = binding.etCompanyName.text.toString()
                val manager = binding.etManager.text.toString()

                if (companyName.isEmpty()) {
                    Toast.makeText(context, "회사명을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }else if (manager.isEmpty()) {
                    Toast.makeText(context, "담당자 이름을 입력해 주세요.", Toast.LENGTH_SHORT).show()
                }else {
                    ApiClient.service.findId(Encoder.urlEncode(companyName).toString(), Encoder.urlEncode(manager).toString())
                        .enqueue(object : Callback<UserFindDTO> {
                            override fun onResponse(call: Call<UserFindDTO>, response: Response<UserFindDTO>) {
                                Log.d(TAG, "아이디 찾기 url => $response")
                                Log.d(TAG, "회사명 => $companyName // 담당자 이름 => $manager")

                                if (response.isSuccessful) {
                                    when (response.body()!!.status) {
                                        1 -> {
                                            binding.run {
                                                tvResultId.text = response.body()!!.userid
                                                llResult.visibility = View.VISIBLE
                                                llIdFind.visibility = View.GONE
                                                btnSave.text = "로그인"
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

                if (binding.btnSave.text == "로그인") {
                    val intent = Intent(context, LoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                    startActivity(intent)

//                    activity?.finish()
                }
            }
        }
    }


}