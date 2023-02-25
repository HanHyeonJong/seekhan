package com.wooriyo.seekhan.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.wooriyo.seekhan.MyApplication
import com.wooriyo.seekhan.R
import com.wooriyo.seekhan.api.ApiClient
import com.wooriyo.seekhan.databinding.ActivityGoodsDetailBinding
import com.wooriyo.seekhan.model.GoodsDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.math.round

class GoodsDetailActivity : AppCompatActivity() {
    private val TAG = "Goods"

    //뷰 바인딩
    private lateinit var binding: ActivityGoodsDetailBinding

    //진행상태 여부
    var check1 = true
    var check2 = false

    lateinit var idx: String
    private val useridx = MyApplication.pref.getUserIdx().toString()
    lateinit var method: String
    lateinit var goodstype: String
    private val userlv = MyApplication.pref.getuserlv()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoodsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intent = intent

        binding.run {

            Log.d(TAG, "유저레벨 => $userlv")

            llBack.setOnClickListener { finish() }
            ivBack.setOnClickListener { finish() }

            when (userlv) {
                "4" -> binding.progress.visibility = View.VISIBLE
                else -> binding.progress.visibility = View.GONE
            }

            //스캔시
            if (intent.getStringExtra("scan") == "scan") {
                Log.d(TAG, "업체명 => ${intent.getStringExtra("cmpname")}")

                headerTitle.text = getString(R.string.scan_result)
                tvTitle.text = intent.getStringExtra("title")
                tvCmpName.text = getString(R.string.company) + intent.getStringExtra("cmpname")
                tvSaveMethod.text = intent.getStringExtra("savemethod")
                tvWeight.text = intent.getStringExtra("weight")
                tvHsCode.text = intent.getStringExtra("hscode")
                tvCbm.text = (if (intent.getStringExtra("cbm").isNullOrEmpty()) {
                    "0"
                } else {
                    String.format("%.2f", intent.getStringExtra("cbm")?.toFloat())
                }).toString()
                tvStatus.text = intent.getStringExtra("goods_status")
                tvRegdt.text = intent.getStringExtra("regdt")

                idx = intent.getStringExtra("idx").toString()
                goodstype = intent.getStringExtra("goodstype").toString()
            }else {
                //히스토리 클릭
                Log.d(TAG, "업체명 => ${intent.getStringExtra("cmpname")}")

                tvTitle.text = intent.getStringExtra("title")
                tvCmpName.text = getString(R.string.company) + intent.getStringExtra("cmpname")
                tvSaveMethod.text = intent.getStringExtra("savemethod")
                tvHsCode.text = intent.getStringExtra("hsCode")
                tvStatus.text = intent.getStringExtra("status")
                tvWeight.text = intent.getStringExtra("weight")
                tvCbm.text = (if (intent.getStringExtra("cbm").isNullOrEmpty()) {
                    "0"
                } else {
                    String.format("%.2f", intent.getStringExtra("cbm")?.toFloat())
                }).toString()

                tvRegdt.text = intent.getStringExtra("regdt")

                idx = intent.getStringExtra("idx").toString()
                goodstype = intent.getStringExtra("goodstype").toString()
            }

            btnResult.setOnClickListener { finish() }

            ivCheck1.setOnClickListener { check1() }
            tvCheck1.setOnClickListener { check1() }

            ivCheck2.setOnClickListener { check2() }
            tvCheck2.setOnClickListener { check2() }

            btnModify.setOnClickListener {
                if (check1) {
                    method = "S"
                }else if (check2) {
                    method = "D"
                }

                //진행상태 변경 API
                //todo goodstype 확인 해야함
                ApiClient.service.udtMethod(idx, useridx, method, goodstype)
                    .enqueue(object : Callback<GoodsDTO> {
                        override fun onResponse(call: Call<GoodsDTO>, response: Response<GoodsDTO>) {
                            Log.d(TAG, "진행상태 변경 url => $response")
                            Log.d(TAG, "진행상태 변경 status => ${response.body()?.status}")
                            Log.d(TAG, "히스토리 idx => $idx // 유저idx => $useridx // 진행상태 => $method // fcl lcl => $goodstype")

                            if (response.isSuccessful) {
                                if (response.body()!!.status == 1) {
                                    Toast.makeText(this@GoodsDetailActivity, "변경 되었습니다.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<GoodsDTO>, t: Throwable) {
                            Log.d(TAG, t.toString())
                            Toast.makeText(this@GoodsDetailActivity, "다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }

    private fun ActivityGoodsDetailBinding.check2() {
        if (!check2) {
            check1 = false
            check2 = true
            ivCheck1.setImageResource(R.drawable.btn_radio_off)
            ivCheck2.setImageResource(R.drawable.btn_radio_on)
        }
    }

    private fun ActivityGoodsDetailBinding.check1() {
        if (!check1) {
            check1 = true
            check2 = false
            ivCheck1.setImageResource(R.drawable.btn_radio_on)
            ivCheck2.setImageResource(R.drawable.btn_radio_off)
        }
    }

}