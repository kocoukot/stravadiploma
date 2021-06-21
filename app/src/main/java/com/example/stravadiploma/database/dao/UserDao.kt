package com.example.stravadiploma.database.dao

import androidx.room.*
import com.example.stravadiploma.data.UserForActivity
import com.example.stravadiploma.data.UserProfile
import com.example.stravadiploma.database.contracts.ActivityContract
import com.example.stravadiploma.database.contracts.UserContract

@Dao
interface UserDao {

    @Query("SELECT * FROM ${UserContract.TABLE_NAME}")
    suspend fun getUser(): UserProfile

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(userProfile: UserProfile): Long

    @Query("DELETE FROM ${UserContract.TABLE_NAME}")
    suspend fun deleteUser()

    @Query("DELETE FROM ${ActivityContract.TABLE_NAME}")
    suspend fun deleteActivity()


}