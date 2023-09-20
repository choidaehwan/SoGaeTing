package com.choidaehwan.sogaeting.message

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.choidaehwan.sogaeting.R
import com.choidaehwan.sogaeting.databinding.ActivityMyLikeListBinding
import com.choidaehwan.sogaeting.utils.FirebaseAuthUtils
import com.choidaehwan.sogaeting.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MyLikeListActivity : AppCompatActivity() {

    private lateinit var myLikeListBinding: ActivityMyLikeListBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myLikeListBinding = ActivityMyLikeListBinding.inflate(layoutInflater)
        setContentView(myLikeListBinding.root)

        getMyLikeList()

    }

    private fun getMyLikeList() {
        FirebaseRef.userLikeRef.child(FirebaseAuthUtils.getUid()).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children) {

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}