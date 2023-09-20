package com.choidaehwan.sogaeting.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.choidaehwan.sogaeting.R
import com.choidaehwan.sogaeting.auth.IntroActivity
import com.choidaehwan.sogaeting.databinding.ActivitySettingBinding
import com.choidaehwan.sogaeting.message.MyLikeListActivity
import com.choidaehwan.sogaeting.utils.FirebaseAuthUtils

class SettingActivity : AppCompatActivity() {

    private lateinit var settingBinding: ActivitySettingBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        settingBinding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(settingBinding.root)

        goMypage()
        goMyMatchingList()
        doLogout()
    }

    fun goMypage() {
        settingBinding.myPageBtn.setOnClickListener {
            val intent = Intent(this, MyPageActivity::class.java)
            startActivity(intent)
        }
    }

    fun doLogout() {
        settingBinding.logoutBtn.setOnClickListener {
            FirebaseAuthUtils.auth.signOut()
            Toast.makeText(this, "로그아웃되었습니다", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, IntroActivity::class.java)
            intent.flags =
                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    fun goMyMatchingList() {
        settingBinding.myLikeList.setOnClickListener {
            val intent = Intent(this, MyLikeListActivity::class.java)
            startActivity(intent)
        }
    }
}