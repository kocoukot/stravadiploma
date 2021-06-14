package com.example.stravadiploma.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.stravadiploma.data.UserProfile
import com.example.stravadiploma.database.contracts.ActivityContract
import com.example.stravadiploma.database.contracts.UserContract


@Dao
interface ActivityDao {

    @Query("SELECT * FROM ${ActivityContract.TABLE_NAME}")
    suspend fun getAllStudents() //: UserProfile
}