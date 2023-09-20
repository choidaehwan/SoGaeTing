package com.choidaehwan.sogaeting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.bokchi.sogating_final.slider.CardStackAdapter
import com.bumptech.glide.Glide
import com.choidaehwan.sogaeting.auth.IntroActivity
import com.choidaehwan.sogaeting.auth.UserDataModel
import com.choidaehwan.sogaeting.databinding.ActivityMainBinding
import com.choidaehwan.sogaeting.setting.SettingActivity
import com.choidaehwan.sogaeting.utils.FirebaseAuthUtils
import com.choidaehwan.sogaeting.utils.FirebaseRef
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    lateinit var cardStackAdapter: CardStackAdapter
    lateinit var manager: CardStackLayoutManager
    private val userDataModel = mutableListOf<UserDataModel>()
    private val itemKeyList = ArrayList<String>()
    private var userCount = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        goSetting()
        getUserDataList()

        manager = CardStackLayoutManager(baseContext, object: CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {
                if (direction == Direction.Right) {
                    Toast.makeText(baseContext, "right", Toast.LENGTH_SHORT).show()
                }
                if (direction == Direction.Left) {
                    Toast.makeText(baseContext, "left", Toast.LENGTH_SHORT).show()
                }

                userCount += 1
                if (userCount == userDataModel.count()) {
                    getUserDataList()
                    Toast.makeText(baseContext, "refresh", Toast.LENGTH_LONG).show()
                }

            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }

        })

        cardStackAdapter = CardStackAdapter(baseContext, itemKeyList, userDataModel)
        mainBinding.cardStackView.layoutManager = manager
        mainBinding.cardStackView.adapter = cardStackAdapter
    }

    fun goSetting() {
        mainBinding.settingIcon.setOnClickListener {

            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getUserDataList() {
        // Read from the database
        FirebaseRef.userInfoRef
            //.child(FirebaseAuthUtils.getUid())
            .addValueEventListener(object :
            ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                userDataModel.clear()

                val dataModel = dataSnapshot.getValue(UserDataModel::class.java)!!
                Log.d("myLog", dataModel.toString())

                for (dataModel in dataSnapshot.children) {
                    userDataModel.add(dataModel.getValue(UserDataModel::class.java)!!)
                    itemKeyList.add(dataModel.key.toString())
                }
                cardStackAdapter.notifyDataSetChanged()

            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}