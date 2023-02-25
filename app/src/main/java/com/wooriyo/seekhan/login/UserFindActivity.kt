package com.wooriyo.seekhan.login

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import androidx.appcompat.app.AppCompatActivity
import com.wooriyo.seekhan.R
import com.wooriyo.seekhan.databinding.ActivityUserFindBinding
import com.wooriyo.seekhan.login.fragment.IdFragment
import com.wooriyo.seekhan.login.fragment.PwdFragment

class UserFindActivity : AppCompatActivity(), OnClickListener {

    private val TAG = "UserFind"

    //뷰 바인딩
    lateinit var binding: ActivityUserFindBinding

    //프래그먼트
    private var idFragment = IdFragment()
    private var pwdFragment = PwdFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserFindBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idSelect()

        binding.tvIdFind.setOnClickListener(this@UserFindActivity)
        binding.tvPwdFind.setOnClickListener(this@UserFindActivity)
        binding.llBack.setOnClickListener(this@UserFindActivity)
        binding.ivBack.setOnClickListener(this@UserFindActivity)

    }

    override fun onClick(p0: View?) {
        when(p0?.id) {
            binding.tvIdFind.id -> { idSelect() }
            binding.tvPwdFind.id -> { pwdSelect() }
            binding.llBack.id -> back()
            binding.ivBack.id -> back()
        }
    }

    private fun back() {
        val intent = Intent(this@UserFindActivity, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    //뒤로가기 버튼
    override fun onBackPressed() {
        super.onBackPressed()
        back()
    }

    //비밀번호 찾기 선택
    private fun pwdSelect() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, pwdFragment)
            .addToBackStack(null)
            .commit()

        binding.run {
            tvPwdFind.setBackgroundColor(Color.WHITE)
            tvPwdFind.setTextColor(Color.parseColor("#009FDE"))
            tvPwdFind.typeface = Typeface.DEFAULT_BOLD
            tvIdFind.setBackgroundColor(Color.parseColor("#F9F9F9"))
            tvIdFind.setTextColor(Color.BLACK)
            tvIdFind.typeface = Typeface.DEFAULT
        }


    }

    //아이디 찾기 선택
    private fun idSelect() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment, idFragment)
            .addToBackStack(null)
            .commit()

        binding.run {
            tvIdFind.setBackgroundColor(Color.WHITE)
            tvIdFind.setTextColor(Color.parseColor("#009FDE"))
            tvIdFind.typeface = Typeface.DEFAULT_BOLD
            tvPwdFind.setBackgroundColor(Color.parseColor("#F9F9F9"))
            tvPwdFind.setTextColor(Color.BLACK)
            tvPwdFind.typeface = Typeface.DEFAULT
        }
    }
}