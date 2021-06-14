package com.example.stravadiploma.database.contracts

import com.example.stravadiploma.data.UserSex
import com.squareup.moshi.Json

object UserContract {
    const val TABLE_NAME = "user"

    object Columns{
        const val USER_ID = "id"
        const val USER_NICK_NAME = "user_nick_name"
        const val USER_FIRST_NAME = "user_first_name"
        const val USER_LAST_NAME = "user_last_name"
        const val USER_COUNTRY = "user_country"
        const val USER_SEX = "user_sex"
        const val USER_FOLLOWERS_COUNT = "user_followers"
        const val USER_FRIENDS_COUNT = "user_friends"
        const val USER_WEIGHT = "user_weight"
        const val USER_AVATAR = "user_avatar"
    }
}