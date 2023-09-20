package com.choidaehwan.sogaeting.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.choidaehwan.sogaeting.MainActivity
import com.choidaehwan.sogaeting.R
import com.choidaehwan.sogaeting.databinding.ActivityLoginBinding
import com.choidaehwan.sogaeting.utils.FirebaseAuthUtils

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)

        loginBinding.loginBtn.setOnClickListener {

            val getEmail = loginBinding.emailArea.text.toString()
            val getPwd = loginBinding.pwdArea.text.toString()

            FirebaseAuthUtils.auth.signInWithEmailAndPassword(getEmail, getPwd)
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                        Toast.makeText(baseContext, "로그인 성공", Toast.LENGTH_SHORT,).show()
                    } else {
                        Toast.makeText(baseContext, "로그인 실패", Toast.LENGTH_SHORT,).show()
                    }
                }

        }
    }
}