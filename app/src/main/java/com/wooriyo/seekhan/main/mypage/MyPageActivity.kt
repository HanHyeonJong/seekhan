package com.wooriyo.seekhan.main.mypage

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.wooriyo.seekhan.databinding.ActivityMyPageBinding
import com.wooriyo.seekhan.login.LoginActivity


class MyPageActivity : AppCompatActivity(), OnClickListener {

    lateinit var binding: ActivityMyPageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.run {
            tvMember.setOnClickListener(this@MyPageActivity)
            tvLogout.setOnClickListener(this@MyPageActivity)
            tvPwd.setOnClickListener(this@MyPageActivity)
            llBack.setOnClickListener(this@MyPageActivity)
            ivBack.setOnClickListener(this@MyPageActivity)
        }
    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.tvLogout.id -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("로그아웃")
                    .setMessage("로그아웃 하시겠습니까?")
                    .setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, id ->
                            val intent = Intent(this@MyPageActivity, LoginActivity::class.java)
                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                            startActivity(intent)

                            val pref = getSharedPreferences("seekhan_Pref_key", MODE_PRIVATE)
                            val editor = pref.edit()

                            editor.remove("userid")
                            editor.remove("userpass")
                            editor.apply()
                        })
                    .setNegativeButton("취소",
                        DialogInterface.OnClickListener { dialog, id ->

                        })
                // 다이얼로그를 띄워주기
                builder.show()
            }

            binding.tvPwd.id -> {
                val intent = Intent(this@MyPageActivity, PwdChangeActivity::class.java)
                startActivity(intent)
            }

            binding.tvMember.id -> {
                val intent = Intent(this@MyPageActivity, MemberInfoActivity::class.java)
                startActivity(intent)
            }

            binding.llBack.id -> finish()
            binding.ivBack.id -> finish()
        }
    }
}