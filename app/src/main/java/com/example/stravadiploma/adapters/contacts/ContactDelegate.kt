package com.example.stravadiploma.adapters.contacts

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stravadiploma.R
import com.example.stravadiploma.data.Contact
import com.example.stravadiploma.utils.inflate
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer


class ContactDelegate
    (
    private val onItemClicked: (contactId: Int) -> Unit
) :
    AbsListItemAdapterDelegate<Contact, Contact, ContactDelegate.CommonHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): CommonHolder {
        return CommonHolder(
            parent.inflate(R.layout.item_contact), onItemClicked
        )
    }

    override fun isForViewType(
        item: Contact,
        items: MutableList<Contact>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(
        item: Contact,
        holder: CommonHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class CommonHolder(
        override val containerView: View,
        onItemClicked: (contactId: Int) -> Unit
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        init {
            containerView.setOnClickListener {
                onItemClicked(bindingAdapterPosition)
            }
        }

        fun bind(contact: Contact) {
            val contactName = containerView.findViewById<TextView>(R.id.contactName)
            val contactNumber = containerView.findViewById<TextView>(R.id.contactNumber)
            contactName.text = contact.name
            contact.number.forEach { number ->
                contactNumber.text = "${contactNumber.text} $number\n"
            }
//

        }
    }
}