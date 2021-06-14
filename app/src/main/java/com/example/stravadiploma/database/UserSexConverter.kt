package com.example.stravadiploma.database

import androidx.room.TypeConverter
import com.example.stravadiploma.data.UserSex

class UserSexConverter {

    @TypeConverter
    fun convertYearToString(userSex: UserSex): String = userSex.name

    @TypeConverter
    fun convertStringToYear(userSexString: String): UserSex = UserSex.valueOf(userSexString)
}
