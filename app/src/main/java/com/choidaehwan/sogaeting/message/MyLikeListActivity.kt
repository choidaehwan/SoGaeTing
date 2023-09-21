package com.choidaehwan.sogaeting.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.choidaehwan.sogaeting.R
import com.choidaehwan.sogaeting.auth.UserDataModel
import com.choidaehwan.sogaeting.databinding.ActivityMyLikeListBinding
import com.choidaehwan.sogaeting.utils.FirebaseAuthUtils
import com.choidaehwan.sogaeting.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyLikeListActivity : AppCompatActivity() {

    private lateinit var myLikeListBinding: ActivityMyLikeListBinding
    private val userDataModel = mutableListOf<UserDataModel>()
    private val likeUserList = mutableListOf<String>()
    lateinit var rvAdapter : RecycleViewAdpater
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myLikeListBinding = ActivityMyLikeListBinding.inflate(layoutInflater)
        setContentView(myLikeListBinding.root)

        rvAdapter = RecycleViewAdpater(baseContext, userDataModel)

        // 리사이클뷰에 어댑터 세팅
        myLikeListBinding.userRecyclerView.adapter = rvAdapter
        myLikeListBinding.userRecyclerView.layoutManager = LinearLayoutManager(baseContext)

        getMyLikeList()



        Log.d("why", userDataModel.toString())

    }

    private fun getMyLikeList() {
        FirebaseRef.userLikeRef.child(FirebaseAuthUtils.getUid()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children) {
                    likeUserList.add(dataModel.key.toString())
                }
                getUserDataList()
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    private fun getUserDataList() {
        FirebaseRef.userInfoRef
            //.child(otherKey)
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    for (dataModel in dataSnapshot.children) {
                        val user = dataModel.getValue(UserDataModel::class.java)!!
                        if (likeUserList.contains(user.uid)) {
                            userDataModel.add(dataModel.getValue(UserDataModel::class.java)!!)
                        }
                    }
                    rvAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(error: DatabaseError) {
                }
            })
    }
}