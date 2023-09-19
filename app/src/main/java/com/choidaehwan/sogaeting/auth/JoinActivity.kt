package com.choidaehwan.sogaeting.auth

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import com.choidaehwan.sogaeting.MainActivity
import com.choidaehwan.sogaeting.R
import com.choidaehwan.sogaeting.databinding.ActivityIntroBinding
import com.choidaehwan.sogaeting.databinding.ActivityJoinBinding
import com.choidaehwan.sogaeting.utils.FirebaseAuthUtils
import com.choidaehwan.sogaeting.utils.FirebaseRef
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class JoinActivity : AppCompatActivity() {

    private lateinit var joinBinding: ActivityJoinBinding
    private lateinit var auth: FirebaseAuth
    private val userDataModel = mutableListOf<UserDataModel>()

    private var emailFlag = false
    private var pwdFlag = false
    private var pwdCheckFlag = false

    private var nickname = ""
    private var gender = ""
    private var city = ""
    private var age = ""
    private var uid = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        joinBinding = ActivityJoinBinding.inflate(layoutInflater)
        setContentView(joinBinding.root)

        auth = Firebase.auth

        emailErrorCheck()
        pwdErrorCheck()
        pwdDuplicateCheck()
        join()

    }
    fun join() {
        joinBinding.joinBtn.setOnClickListener {

            val getEmail = joinBinding.emailArea.text.toString()
            val getPwd = joinBinding.pwdArea.text.toString()
            val getPwdChk = joinBinding.pwdCheckArea.text.toString()

            nickname = joinBinding.nicknameArea.text.toString()
            gender = joinBinding.genderArea.text.toString()
            city = joinBinding.cityArea.text.toString()
            age = joinBinding.ageArea.text.toString()

            if (emailFlag && pwdFlag && pwdCheckFlag) {
                auth.createUserWithEmailAndPassword(getEmail, getPwd)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)

                            uid = auth.currentUser?.uid.toString()
                            FirebaseRef.userInfoRef.child(uid)
                                .setValue(UserDataModel(nickname, gender, city, age))
                            Toast.makeText(baseContext, "회원가입 완료", Toast.LENGTH_SHORT,).show()
                        } else {
                            Toast.makeText(baseContext, "회원가입 실패", Toast.LENGTH_SHORT,).show()
                        }
                    }
            }
        }
    }

    fun emailErrorCheck() {
        joinBinding.emailArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"  // 이메일 정규 표현식
                val emailMinLenth = 5  // 원하는 최소 길이

                if (s.length < emailMinLenth) {
                    emailFlag = false
                    joinBinding.emailArea.error = "최소 ${emailMinLenth}글자 이상 입력해야 합니다."
                } else if (!s.toString().trim { it <= ' ' }.matches(emailPattern.toRegex())) {
                    emailFlag = false
                    joinBinding.emailArea.error = "유효한 이메일 주소를 입력해주세요."
                } else {
                    emailFlag = true
                    joinBinding.emailArea.error = null  // 에러 메시지 삭제
                }
            }
        })
    }

    fun pwdErrorCheck() {
        joinBinding.pwdArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                val pwdMinLenth = 6
                if (s.length < pwdMinLenth) {  // MIN_LENGTH는 원하는 최소 길이입니다.
                    pwdFlag = false
                    joinBinding.pwdArea.error = "최소 ${pwdMinLenth}글자 이상 입력해야 합니다."
                } else {
                    pwdFlag = true
                    joinBinding.pwdArea.error = null  // 에러 메시지 삭제
                }
            }
        })
    }

    fun pwdDuplicateCheck() {
        joinBinding.pwdCheckArea.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable) {
                if (s.toString() != joinBinding.pwdArea.text.toString()) {
                    pwdCheckFlag = false
                    joinBinding.pwdCheckArea.error = "비밀번호가 일치하지 않습니다."
                } else {
                    pwdCheckFlag = true
                    joinBinding.pwdCheckArea.error = null  // 에러 메시지 삭제
                }
            }
        })

    }
}