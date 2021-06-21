package com.example.stravadiploma.adapters.activities

import androidx.recyclerview.widget.DiffUtil
import com.example.stravadiploma.data.ActivityData
import com.example.stravadiploma.data.UserForActivity
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ActivityAdapter(
    private val user: UserForActivity
):
    AsyncListDifferDelegationAdapter<ActivityData>(ActivityDiffUtilCallBack()) {
    init {
        delegatesManager.addDelegate(ActivityDelegate(user))
    }

    class ActivityDiffUtilCallBack : DiffUtil.ItemCallback<ActivityData>() {
        override fun areItemsTheSame(
            oldItem: ActivityData,
            newItem: ActivityData
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ActivityData,
            newItem: ActivityData
        ): Boolean {
            return oldItem == newItem
        }

    }
}