package com.example.stravadiploma.data

import androidx.room.ColumnInfo
import com.example.stravadiploma.database.contracts.UserContract
import com.squareup.moshi.Json

data class UserForActivity(

    @ColumnInfo(name = UserContract.Columns.USER_LAST_NAME)
    val lastname: String,

    @ColumnInfo(name = UserContract.Columns.USER_FIRST_NAME)
    val firstname: String,

    @ColumnInfo(name = UserContract.Columns.USER_AVATAR)
    val avatar: String
)