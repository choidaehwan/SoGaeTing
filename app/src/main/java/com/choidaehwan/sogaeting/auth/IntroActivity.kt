package com.choidaehwan.sogaeting.auth

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.choidaehwan.sogaeting.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    private lateinit var introBinding: ActivityIntroBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        introBinding = ActivityIntroBinding.inflate(layoutInflater)
        setContentView(introBinding.root)

        val isTiramisuOrHigher = Build.VERSION.SDK_INT>= Build.VERSION_CODES.TIRAMISU
        val notificationPermission = Manifest.permission.POST_NOTIFICATIONS

        var hasNotificationPermission =
            if (isTiramisuOrHigher)
                ContextCompat.checkSelfPermission(this, notificationPermission) == PackageManager.PERMISSION_GRANTED
            else true

        val launcher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            hasNotificationPermission = it

        }

        if (!hasNotificationPermission) {
            launcher.launch(notificationPermission)
        }

        introBinding.joinBtn.setOnClickListener {
            val intent = Intent(this, JoinActivity::class.java)
            startActivity(intent)
        }

        introBinding.loginBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }
    }
}