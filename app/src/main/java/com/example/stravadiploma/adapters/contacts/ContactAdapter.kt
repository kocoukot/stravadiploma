package com.example.stravadiploma.adapters.contacts

import androidx.recyclerview.widget.DiffUtil
import com.example.stravadiploma.data.Contact
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class ContactAdapter
    (
    private val onItemClicked: (contactId: Int) -> Unit
) :
    AsyncListDifferDelegationAdapter<Contact>(ContactsDiffUtilCallBack()) {
    init {
        delegatesManager.addDelegate(ContactDelegate(onItemClicked))
    }

    class ContactsDiffUtilCallBack : DiffUtil.ItemCallback<Contact>() {
        override fun areItemsTheSame(
            oldItem: Contact,
            newItem: Contact
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: Contact,
            newItem: Contact
        ): Boolean {
            return oldItem == newItem
        }

    }
}