package com.example.stravadiploma.data

import com.squareup.moshi.Json

enum class UserSex {
    @Json(name = "F")
    Female,
    @Json(name = "M")
    Male,
}