package com.choidaehwan.sogaeting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import com.choidaehwan.sogaeting.auth.IntroActivity
import com.choidaehwan.sogaeting.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var splashBinding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        splashBinding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(splashBinding.root)

        Handler(Looper.getMainLooper()).postDelayed(Runnable {
            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
    }
}