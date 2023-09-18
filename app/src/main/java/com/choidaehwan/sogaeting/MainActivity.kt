package com.choidaehwan.sogaeting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bokchi.sogating_final.slider.CardStackAdapter
import com.choidaehwan.sogaeting.databinding.ActivityMainBinding
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    private lateinit var mainBinding: ActivityMainBinding
    lateinit var cardStackAdapter: CardStackAdapter
    lateinit var manager: CardStackLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainBinding.root)

        manager = CardStackLayoutManager(baseContext, object: CardStackListener {
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {

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
        val testList = mutableListOf<String>()
        testList.add("a")
        testList.add("b")
        testList.add("c")

        cardStackAdapter = CardStackAdapter(baseContext, testList)
        mainBinding.cardStackView.layoutManager = manager
        mainBinding.cardStackView.adapter = cardStackAdapter
    }
}