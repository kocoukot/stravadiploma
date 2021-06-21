package com.example.stravadiploma.data

import androidx.room.ColumnInfo
import com.example.stravadiploma.database.contracts.UserContract

data class UserForActivity(

    @ColumnInfo(name = UserContract.Columns.USER_LAST_NAME)
    val lastName: String,

    @ColumnInfo(name = UserContract.Columns.USER_FIRST_NAME)
    val firstName: String,

    @ColumnInfo(name = UserContract.Columns.USER_AVATAR)
    val avatar: String
)