package com.choidaehwan.sogaeting.message

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.choidaehwan.sogaeting.auth.UserDataModel
import com.choidaehwan.sogaeting.databinding.RecycleViewItemBinding
import com.choidaehwan.sogaeting.utils.FirebaseAuthUtils
import com.choidaehwan.sogaeting.utils.FirebaseRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class RecycleViewAdpater (
    val context: Context,
    val userDataModel: MutableList<UserDataModel>,
) : RecyclerView.Adapter<RecycleViewAdpater.ViewHolder>() {
    inner class ViewHolder(private val recycleViewItemBinding: RecycleViewItemBinding): RecyclerView.ViewHolder(recycleViewItemBinding.root) {
        fun bindItems(userDataModel: UserDataModel) {
            Log.d("myrv", userDataModel.nickname.toString())
            recycleViewItemBinding.recyclerViewItemNickname.text = userDataModel.nickname

            itemView.setOnClickListener {
                checkMatching(userDataModel.uid.toString())
            }
        }

        private fun checkMatching(otherUid: String) {
            FirebaseRef.userLikeRef.child(otherUid).addValueEventListener(object :
                ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.children.count() == 0) {
                        Toast.makeText(context, "소식이 없습니다...", Toast.LENGTH_SHORT).show()
                    } else {
                        for (dataModel in dataSnapshot.children) {
                            if (dataModel.key.equals(FirebaseAuthUtils.getUid())) {
                                Toast.makeText(context, "매칭!!!!!!!!!!!!!!!!", Toast.LENGTH_LONG)
                                    .show()

                            } else {
                                Toast.makeText(context, "소식이 없습니다...", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecycleViewAdpater.ViewHolder {
        val recycleViewItemBinding = RecycleViewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(recycleViewItemBinding)
    }

    override fun getItemCount(): Int {
        return userDataModel.size
    }

    override fun onBindViewHolder(holder: RecycleViewAdpater.ViewHolder, position: Int) {
        holder.bindItems(userDataModel[position])
    }

}