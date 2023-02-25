package com.wooriyo.seekhan.main.mypage

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.wooriyo.seekhan.MyApplication
import com.wooriyo.seekhan.databinding.ActivityMemberInfoBinding
import com.wooriyo.seekhan.login.WebViewActivity

class MemberInfoActivity : AppCompatActivity() {

    //뷰 바인딩
    lateinit var binding: ActivityMemberInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemberInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            llBack.setOnClickListener { finish() }
            ivBack.setOnClickListener { finish() }

            tvCmpName.text = MyApplication.pref.getCmpName()
            tvCmpNum.text = MyApplication.pref.getcmpnum()
            tvCmpAddr.text = MyApplication.pref.getcmpaddr()
            tvId.text = MyApplication.pref.getUserId()
            tvCmpTel.text = MyApplication.pref.getcmptel()
            tvCmpFax.text = MyApplication.pref.getcmpfax()
            tvCmpPhone.text = MyApplication.pref.getcmpphone()
            tvManagerName.text = MyApplication.pref.getname()

            tvTaol.setOnClickListener {
                val intent = Intent(this@MemberInfoActivity, WebViewActivity::class.java)
                startActivity(intent)
            }
        }
    }
}