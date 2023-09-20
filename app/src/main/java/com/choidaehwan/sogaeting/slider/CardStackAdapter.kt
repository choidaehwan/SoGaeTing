package com.bokchi.sogating_final.slider

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.choidaehwan.sogaeting.R
import com.choidaehwan.sogaeting.auth.UserDataModel
import com.choidaehwan.sogaeting.databinding.ItemCardBinding
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class CardStackAdapter(
        val context : Context,
        val itemKeyList : ArrayList<String>,
        val items : List<UserDataModel>
    ) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {
        val itemCardBinding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemCardBinding)
    }
    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        holder.binding(items[position], itemKeyList[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(
        private val itemCardBinding: ItemCardBinding
    ) : RecyclerView.ViewHolder(itemCardBinding.root) {

        fun binding(data : UserDataModel, key: String) {
            itemCardBinding.itemNickname.text = data.nickname
            itemCardBinding.itemAge.text = data.age
            itemCardBinding.itemCity.text = data.city

            Log.d("myLog", key)
            getImageData(key)

        }

        fun getImageData(key: String) {
            // Reference to an image file in Cloud Storage
            val storageReference = Firebase.storage.reference.child(key)

            storageReference.downloadUrl.addOnCompleteListener(OnCompleteListener {
                    task ->
                if (task.isSuccessful) {
                    Glide.with(context)
                        .load(task.result)
                        .into(itemCardBinding.profileImageArea)
                } else {
                    //itemCardBinding.profileImageArea.isVisible = false
                }
            } )
        }

    }

}