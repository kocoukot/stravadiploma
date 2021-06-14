package com.example.stravadiploma.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.stravadiploma.data.UserProfile
import com.example.stravadiploma.database.dao.ActivityDao
import com.example.stravadiploma.database.dao.UserDao


@Database(
    entities = [
        UserProfile::class
    ], version = StravaDataBase.DB_VERSION
)

abstract class StravaDataBase: RoomDatabase() {

    abstract fun userDao(): UserDao
   // abstract fun activityDao(): ActivityDao

    companion object {
        const val DB_VERSION = 1
        const val DB_NAME = "strava-database"

    }
}