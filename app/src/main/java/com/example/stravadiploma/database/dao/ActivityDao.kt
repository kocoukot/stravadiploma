package com.example.stravadiploma.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.stravadiploma.data.ActivityData
import com.example.stravadiploma.data.UserForActivity
import com.example.stravadiploma.database.contracts.ActivityContract
import com.example.stravadiploma.database.contracts.UserContract


@Dao
interface ActivityDao {

    @Query("SELECT * FROM ${ActivityContract.TABLE_NAME} ORDER BY activity_start_date DESC")
    suspend fun getAllActivities() : List<ActivityData>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addActivity(activities: List<ActivityData>)

    @Query("SELECT ${UserContract.Columns.USER_FIRST_NAME}, ${UserContract.Columns.USER_LAST_NAME}, ${UserContract.Columns.USER_AVATAR} FROM ${UserContract.TABLE_NAME}")
    suspend fun getUserName(): UserForActivity
}