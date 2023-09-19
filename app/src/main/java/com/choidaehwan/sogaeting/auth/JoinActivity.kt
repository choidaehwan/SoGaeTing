package com.choidaehwan.sogaeting.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.choidaehwan.sogaeting.R
import com.choidaehwan.sogaeting.databinding.ActivityIntroBinding
import com.choidaehwan.sogaeting.databinding.ActivityJoinBinding

class JoinActivity : AppCompatActivity() {

    private lateinit var joinBinding: ActivityJoinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        joinBinding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(joinBinding.root)

        joinBinding.joinBtn.setOnClickListener {
            val getEmail = joinBinding.emailArea.text.toString()
            val getPwd = joinBinding.pwdArea.text.toString()


        }
    }
}