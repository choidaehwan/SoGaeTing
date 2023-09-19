package com.choidaehwan.sogaeting.auth

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.choidaehwan.sogaeting.R
import com.choidaehwan.sogaeting.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var introBinding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introBinding.root)


    }
}