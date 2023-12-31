package com.choidaehwan.sogaeting

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bokchi.sogating_final.slider.CardStackAdapter
import com.choidaehwan.sogaeting.auth.IntroActivity
import com.choidaehwan.sogaeting.auth.UserDataModel
import com.choidaehwan.sogaeting.databinding.ActivityMainBinding
import com.choidaehwan.sogaeting.setting.SettingActivity
import com.choidaehwan.sogaeting.utils.FirebaseAuthUtils
import com.choidaehwan.sogaeting.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    lateinit var cardStackAdapter: CardStackAdapter
    lateinit var manager: CardStackLayoutManager
    private val userDataModel = mutableListOf<UserDataModel>()
    private val itemKeyList = ArrayList<String>()
    private val roleList = ArrayList<String>()
    private lateinit var currentUserGender: String
    private var userCount = 0
    private var doubleBackToExitPressedOnce = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        goSetting() // 설정화면
        //getRoleData()
        getMyUserData() // 내 정보 가져오기
        // getUserDataList() // 데이터 받아오기

        manager = CardStackLayoutManager(baseContext, object : CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {
                if (direction == Direction.Right) {
                    val otherUserUid = userDataModel[userCount].uid.toString()
                    userLikeOtherUser(FirebaseAuthUtils.getUid(), otherUserUid) // 좋아요 클릭
                }
                if (direction == Direction.Left) {
                }

                userCount += 1
                if (userCount == userDataModel.count()) {
                    getUserDataList(currentUserGender)
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

    private fun getUserDataList(currentUserGender: String) {
        // Read from the database
        FirebaseRef.userInfoRef
            //.child(FirebaseAuthUtils.getUid())
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    userDataModel.clear()

                    val dataModel = dataSnapshot.getValue(UserDataModel::class.java)!!
                    Log.d("elevator", dataModel.toString())
                    Log.d("elevator2", dataModel.gender.toString())


                    for (dataModel in dataSnapshot.children) {
                        val user = dataModel.getValue(UserDataModel::class.java)!!
                        if (!user.gender.equals(currentUserGender)) {
                            Log.d("elevator", dataModel.toString())
                            userDataModel.add(dataModel.getValue(UserDataModel::class.java)!!)
                            itemKeyList.add(dataModel.key.toString())
                        }
////                // 롤변경
//                    if(roleList.contains(dataModel.key.toString())) {
//                        userDataModel.add(dataModel.getValue(UserDataModel::class.java)!!)
//                        itemKeyList.add(dataModel.key.toString())
//                    }
                    }
                    cardStackAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    fun getMyUserData() {
        FirebaseRef.userInfoRef.child(FirebaseAuthUtils.getUid())
            .addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val dataModel = dataSnapshot.getValue(UserDataModel::class.java)!!
                    currentUserGender = dataModel.gender.toString()
                    getUserDataList(currentUserGender)
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
    }

    private fun userLikeOtherUser(myUid: String, otherUid: String) {
        FirebaseRef.userLikeRef.child(myUid).child(otherUid)
            .push()
            .setValue(true)
        getOtherUserLikeList(otherUid)
    }

    private fun getOtherUserLikeList(otherUid: String) {
        FirebaseRef.userLikeRef.child(otherUid).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (dataModel in dataSnapshot.children) {
                    if (dataModel.key.equals(FirebaseAuthUtils.getUid())) {
                        Toast.makeText(baseContext, "매칭!!!!!!!!!!!!!!!!", Toast.LENGTH_LONG).show()
                        createNotificationChannel()
                        sendNotification()
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(baseContext, "없습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this, "한번 더 누르면 나가집니다.", Toast.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({ doubleBackToExitPressedOnce = false }, 2000)
    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "name"
            val descriptionText = "description"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("test_channel", name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)

        }
    }

    private fun sendNotification() {
        try {
            var builder = NotificationCompat.Builder(this, "test_channel")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("매칭완료")
                .setContentText("매칭이 완료되었습니다 저사람도 나를 좋아해요")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            with(NotificationManagerCompat.from(this)) {
                notify(0, builder.build())
            }
        } catch (e: SecurityException) {
        }
    }

}



//    fun getRoleData() {
//        FirebaseRef.userInfoRef.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (dataModel in dataSnapshot.children) {
//                    Log.d("dataModel", dataModel.toString())
//                    if (dataModel.getValue(UserDataModel::class.java)!!.gender == "감독") {
//                        roleList.add(dataModel.key.toString())
//                    }
//                }
//                Log.d("role", roleList.toString())
//                getUserDataList()
//            }
//            override fun onCancelled(error: DatabaseError) {
//            }
//        }
//        )
//    }