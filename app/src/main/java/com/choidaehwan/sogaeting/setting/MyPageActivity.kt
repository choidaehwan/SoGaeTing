package com.choidaehwan.sogaeting.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.bumptech.glide.Glide
import com.choidaehwan.sogaeting.R
import com.choidaehwan.sogaeting.auth.UserDataModel
import com.choidaehwan.sogaeting.databinding.ActivityMyPageBinding
import com.choidaehwan.sogaeting.utils.FirebaseAuthUtils
import com.choidaehwan.sogaeting.utils.FirebaseRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class MyPageActivity : AppCompatActivity() {

    private lateinit var myPageBinding: ActivityMyPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myPageBinding = ActivityMyPageBinding.inflate(layoutInflater)
        setContentView(myPageBinding.root)

        getMyData()
    }

    fun getMyData() {
        FirebaseRef.userInfoRef.child(FirebaseAuthUtils.getUid())
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val dataModel = dataSnapshot.getValue(UserDataModel::class.java)!!

                    myPageBinding.myUid.text = FirebaseAuthUtils.getUid()
                    myPageBinding.myNickname.text = dataModel.nickname
                    myPageBinding.myAge.text = dataModel.age
                    myPageBinding.myCity.text = dataModel.city
                    myPageBinding.myGender.text = dataModel.gender
                    getImageData(FirebaseAuthUtils.getUid())
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    fun getImageData(key: String) {
        // Reference to an image file in Cloud Storage
        val storageReference = Firebase.storage.reference.child(key)

        storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener {
                task ->
            if (task.isSuccessful) {
                Glide.with(baseContext)
                    .load(task.result)
                    .into(myPageBinding.myImage)
            } else {
                //itemCardBinding.profileImageArea.isVisible = false
            }
        } )
    }


}