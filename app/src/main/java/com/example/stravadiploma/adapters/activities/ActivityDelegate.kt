package com.example.stravadiploma.adapters.activities

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.stravadiploma.R
import com.example.stravadiploma.data.ActivityData
import com.example.stravadiploma.data.UserForActivity
import com.example.stravadiploma.utils.inflate
import com.example.stravadiploma.utils.timeFormat
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import kotlinx.android.extensions.LayoutContainer
import java.text.SimpleDateFormat

class ActivityDelegate(
    private val user: UserForActivity
) :
    AbsListItemAdapterDelegate<ActivityData, ActivityData, ActivityDelegate.CommonHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): CommonHolder {
        return CommonHolder(
            parent.inflate(R.layout.item_activity),user)
    }

    override fun isForViewType(
        item: ActivityData,
        items: MutableList<ActivityData>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onBindViewHolder(
        item: ActivityData,
        holder: CommonHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class CommonHolder(
        override val containerView: View,
        private val user: UserForActivity
    ) : RecyclerView.ViewHolder(containerView), LayoutContainer {

        fun bind(activity: ActivityData) {

            val userName = containerView.findViewById<TextView>(R.id.userName)
            val activityStartDate = containerView.findViewById<TextView>(R.id.activityDateCreated)
            val activityName = containerView.findViewById<TextView>(R.id.activityName)
            val activityDistance = containerView.findViewById<TextView>(R.id.activityDistance)
            val activityElapsedTime = containerView.findViewById<TextView>(R.id.activityTime)
            val activityType = containerView.findViewById<TextView>(R.id.activityType)
            val activityDescription = containerView.findViewById<TextView>(R.id.activityDescription)


            val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
            val formatter = SimpleDateFormat("dd.MM.yyyy HH:mm")
            val output: String = formatter.format(parser.parse("${activity.startDate}"))
            userName.text = "${user.lastname} ${user.firstname}"
            activityStartDate.text = output
            activityName.text = activity.name
            " ${String.format("%.2f", activity.distance / 1000).toDouble()} km".also { activityDistance.text = it }
            activityElapsedTime.text = activity.elapsedTime.timeFormat()
            activityType.text = activity.type
            activityDescription.text = activity.description



        }
    }
}