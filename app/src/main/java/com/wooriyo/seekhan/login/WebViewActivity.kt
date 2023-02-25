package com.wooriyo.seekhan.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebViewClient
import com.wooriyo.seekhan.R
import com.wooriyo.seekhan.databinding.ActivityWebViewBinding

class WebViewActivity : AppCompatActivity() {

    //뷰 바인딩
    lateinit var binding: ActivityWebViewBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWebViewBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            webView.apply {
                webViewClient = WebViewClient()
                // 페이지 컨트롤을 위한 기본적인 함수, 다양한 요청, 알림을 수신하는 기능
                settings.apply {
                    javaScriptEnabled = true // 자바스크립트 허용
                    javaScriptCanOpenWindowsAutomatically= true // 자바스크립트가 window.open()을 사용할 수 있도록 설정
                    loadWithOverviewMode= true // html의 컨텐츠가 웹뷰보다 클 경우 스크린 크기에 맞게 조정
                    useWideViewPort= true // 화면 사이즈 맞추기 허용여부
                    setSupportZoom(true) // 화면 줌 허용여부
                    builtInZoomControls= true // 화면 확대 축소 허용여부
                    domStorageEnabled= true // DOM(html 인식) 저장소 허용여부

                    // 파일 허용
                    allowContentAccess= true
                    allowFileAccess= true
                    mixedContentMode= WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
                    loadsImagesAutomatically= true
                }
            }
            webView.loadUrl("http://49.247.22.45/") // url 주소 가져오기


            llBack.setOnClickListener { finish() }
            ivBack.setOnClickListener { finish() }
        }
    }
}