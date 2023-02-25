package com.wooriyo.seekhan.main

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.OnClickListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.zxing.integration.android.IntentIntegrator
import com.wooriyo.seekhan.MyApplication
import com.wooriyo.seekhan.adapter.GoodsAdapter
import com.wooriyo.seekhan.api.ApiClient
import com.wooriyo.seekhan.databinding.ActivityMainBinding
import com.wooriyo.seekhan.main.mypage.MyPageActivity
import com.wooriyo.seekhan.model.GoodsDTO
import com.wooriyo.seekhan.model.HListDTO
import com.wooriyo.seekhan.model.QrScanDTO
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), OnClickListener {

    private val TAG = "Main"

    //뷰 바인딩
    private lateinit var binding: ActivityMainBinding

    //히스토리
    private val hListDTO = ArrayList<HListDTO>()
    private val goodsAdapter = GoodsAdapter(hListDTO, this@MainActivity)

    //뒤로가기 버튼 2번시 종료
    private var mBackWait:Long = 0

    //useridx
    private val useridx = MyApplication.pref.getUserIdx().toString()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //히스토리 가져오기
        getGoodsHistory()

        //히스토리 리사이클러뷰
        initGoodsRecycler()

        binding.scanQr.setOnClickListener(this@MainActivity)
        binding.btnScanQr.setOnClickListener(this@MainActivity)
        binding.ivScanQr.setOnClickListener(this@MainActivity)
        binding.ivMyPage.setOnClickListener(this@MainActivity)
        binding.llMyPage.setOnClickListener(this@MainActivity)

    }

    //히스토리 리스트 가져오기
    private fun getGoodsHistory() {
        ApiClient.service.getHistory(useridx)
            .enqueue(object : Callback<GoodsDTO> {
                override fun onResponse(call: Call<GoodsDTO>, response: Response<GoodsDTO>) {
                    Log.d(TAG, "히스토리 => $response")
                    Log.d(TAG, "받는 값 => ${response.body()?.hlist}")

                    if (response.isSuccessful) {
                        if (response.body()?.status == 1) {
                            hListDTO.clear()
                            hListDTO.addAll(response.body()!!.hlist)
                            goodsAdapter.notifyDataSetChanged()
                        }
                    }
                }

                override fun onFailure(call: Call<GoodsDTO>, t: Throwable) {
                    Log.d(TAG, t.toString())
                    Toast.makeText(this@MainActivity, "다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                }
            })
    }

    //클릭 리스너
    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.scanQr.id -> {
                startScan()
            }
            binding.btnScanQr.id -> {
                startScan()
            }
            binding.ivScanQr.id -> {
                startScan()
            }

            binding.ivMyPage.id -> { myPage() }
            binding.llMyPage.id -> { myPage() }
        }
    }

    //스캔 시작
    private fun startScan() {
        val integrator = IntentIntegrator(this)
        integrator.setBarcodeImageEnabled(true) // 인식한 바코드 사진을 저장하고 경로를 반환
        integrator.captureActivity = QrScanActivity::class.java
        integrator.initiateScan()
    }

    //리사이클러뷰 초기화
    private fun initGoodsRecycler() {
        binding.rvGoods.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            adapter = goodsAdapter
        }
    }

    //스캔하고 나오면 다시 불러오기
    override fun onResume() {
        super.onResume()
        getGoodsHistory()
    }

    //마이페이지로 이동
    private fun myPage() {
        val intent = Intent(this@MainActivity, MyPageActivity::class.java)
        startActivity(intent)
    }

    //뒤로가기 2번 종료
    override fun onBackPressed() {
        if(System.currentTimeMillis() - mBackWait < 2000){
            finish()
            return
        } else{
            Toast.makeText(applicationContext, "'뒤로'버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show()
            mBackWait = System.currentTimeMillis()
        }
    }

    //스캔 결과값
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        Log.d(TAG, "QR스캔 => $requestCode")
        Log.d(TAG, "QR스캔 => $resultCode}")
        Log.d(TAG, "QR스캔 => $data")

        if (result != null) {
            if (result.contents != null) {
                Log.d(TAG, "QR스캔 => $result")
                Log.d(TAG, "QR스캔 => ${result.contents}")
                Log.d(TAG, "QR스캔 => ${result.formatName}")

                ApiClient.service.searchQr(result.contents, useridx)
                    .enqueue(object : Callback<QrScanDTO> {
                        override fun onResponse(call: Call<QrScanDTO>, response: Response<QrScanDTO>) {
                            if (response.isSuccessful) {
                                if (response.body()?.status == 1) {
                                    val intent = Intent(this@MainActivity, GoodsDetailActivity::class.java)
                                    intent.putExtra("scan", "scan")
                                    intent.putExtra("idx", response.body()!!.idx)
                                    intent.putExtra("title", response.body()!!.title)
                                    intent.putExtra("weight", response.body()!!.weight)
                                    intent.putExtra("method", response.body()!!.method)
                                    intent.putExtra("cmpname", response.body()!!.cmpname)
                                    intent.putExtra("hscode", response.body()!!.hscode)
                                    intent.putExtra("cbm", response.body()!!.cbm)
                                    intent.putExtra("cmb", response.body()!!.cmb)
                                    intent.putExtra("goods_status", response.body()!!.goods_status)
                                    intent.putExtra("goodstype", response.body()!!.goodstype)
                                    intent.putExtra("regdt", response.body()!!.regdt)
                                    intent.putExtra("savemethod", response.body()!!.savemethod)

                                    startActivity(intent)
                                }else {
                                    Toast.makeText(this@MainActivity, "QR을 확인해 주세요.", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }

                        override fun onFailure(call: Call<QrScanDTO>, t: Throwable) {
                            Log.d(TAG, t.toString())
                            Toast.makeText(this@MainActivity, "다시 시도해 주세요.", Toast.LENGTH_SHORT).show()
                        }

                    })
            }
        }else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }
}