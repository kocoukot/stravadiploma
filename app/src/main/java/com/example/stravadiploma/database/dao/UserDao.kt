package com.example.stravadiploma.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import com.example.stravadiploma.data.UserProfile
import com.example.stravadiploma.database.contracts.UserContract

@Dao
interface UserDao {

    @Query("SELECT * FROM ${UserContract.TABLE_NAME}")
    suspend fun getAllStudents(): UserProfile

    @Delete()
    suspend fun deleteUser(user: UserProfile)
}