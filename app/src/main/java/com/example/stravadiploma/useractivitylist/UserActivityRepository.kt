package com.example.stravadiploma.useractivitylist

import com.example.stravadiploma.data.ActivityData
import com.example.stravadiploma.data.UserForActivity
import com.example.stravadiploma.database.Database

class UserActivityRepository {

    val userDao = Database.instance.userDao()
    val activityDao = Database.instance.activityDao()


    suspend fun getAllActivities():List<ActivityData>{
        val activityList = com.example.stravadiploma.net.Network.activityApi.getAllActivities()
        activityDao.addActivity(activityList)
        return activityList

    }

    suspend fun getUserName(): UserForActivity{
        return userDao.getUserName()
    }

}