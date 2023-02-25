package com.wooriyo.seekhan.main

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.journeyapps.barcodescanner.CaptureManager
import com.wooriyo.seekhan.databinding.ActivityQrScanBinding

class QrScanActivity : AppCompatActivity() {

    //뷰 바인딩
    lateinit var binding: ActivityQrScanBinding

    private lateinit var capture: CaptureManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQrScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        capture = CaptureManager(this@QrScanActivity, binding.barcodeScanner)
        capture.initializeFromIntent(intent, savedInstanceState)
        capture.decode()

        binding.run {
            llBack.setOnClickListener { finish() }
            ivBack.setOnClickListener { finish() }
        }
    }

    override fun onResume() {
        super.onResume()
        capture.onResume()
    }

    override fun onPause() {
        super.onPause()
        capture.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        capture.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        capture.onSaveInstanceState(outState)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

    }
}