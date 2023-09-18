package com.bokchi.sogating_final.slider

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.choidaehwan.sogaeting.R
import com.choidaehwan.sogaeting.databinding.ItemCardBinding

class CardStackAdapter(
        val context : Context,
        val items : List<String>
    ) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardStackAdapter.ViewHolder {
        val itemCardBinding = ItemCardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(itemCardBinding)
    }
    override fun onBindViewHolder(holder: CardStackAdapter.ViewHolder, position: Int) {
        holder.binding(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class ViewHolder(
        private val itemCardBinding: ItemCardBinding
    ) : RecyclerView.ViewHolder(itemCardBinding.root) {

        fun binding(data : String) {

        }

    }

}