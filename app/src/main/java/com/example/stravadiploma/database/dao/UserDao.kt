package com.example.stravadiploma.database.dao

import androidx.room.*
import com.example.stravadiploma.data.UserForActivity
import com.example.stravadiploma.data.UserProfile
import com.example.stravadiploma.database.contracts.UserContract

@Dao
interface UserDao {

    @Query("SELECT * FROM ${UserContract.TABLE_NAME}")
    suspend fun getUser(): UserProfile

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userProfile: UserProfile): Long

    @Delete
    suspend fun deleteUser(userProfile: UserProfile)

    @Query("SELECT ${UserContract.Columns.USER_FIRST_NAME}, ${UserContract.Columns.USER_LAST_NAME}, ${UserContract.Columns.USER_AVATAR} FROM ${UserContract.TABLE_NAME}")
    suspend fun getUserName(): UserForActivity
}