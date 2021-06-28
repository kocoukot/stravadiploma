package com.example.stravadiploma.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.stravadiploma.database.UserSexConverter
import com.example.stravadiploma.database.contracts.UserContract
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass


@Entity(
    tableName = UserContract.TABLE_NAME

)
@TypeConverters(UserSexConverter::class)

@JsonClass(generateAdapter = true)
data class UserProfile(
    @PrimaryKey
    @ColumnInfo(name = UserContract.Columns.USER_ID)
    val id: Long,

    @ColumnInfo(name = UserContract.Columns.USER_NICK_NAME)
    val username: String? = "",

    @ColumnInfo(name = UserContract.Columns.USER_LAST_NAME)
    @Json(name="lastname")
    val lastName: String,

    @ColumnInfo(name = UserContract.Columns.USER_FIRST_NAME)
    @Json(name="firstname")
    val firstName: String,

    @ColumnInfo(name = UserContract.Columns.USER_COUNTRY)
    val country: String? = "-",

    @ColumnInfo(name = UserContract.Columns.USER_SEX)
    val sex: UserSex? = UserSex.Male,

    @ColumnInfo(name = UserContract.Columns.USER_FOLLOWERS_COUNT)
    @Json(name="follower_count")
    val followerCount: Int? = 0,

    @ColumnInfo(name = UserContract.Columns.USER_FRIENDS_COUNT)
    @Json(name="friend_count")
    val friendCount: Int? = 0,

    @ColumnInfo(name = UserContract.Columns.USER_WEIGHT)
    var weight: Int?,

    @ColumnInfo(name = UserContract.Columns.USER_AVATAR)
    @Json(name="profile_medium")
    val avatar: String
    )
