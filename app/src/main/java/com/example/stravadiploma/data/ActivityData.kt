package com.example.stravadiploma.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.stravadiploma.database.contracts.ActivityContract
import com.example.stravadiploma.database.contracts.UserContract
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.sql.Date
import java.util.*

@Entity(
    tableName = ActivityContract.TABLE_NAME

)

@JsonClass(generateAdapter = true)
data class ActivityData(
    @PrimaryKey
    val id: Long,
    val name: String,
    val type: String,

    @ColumnInfo(name = ActivityContract.Columns.ACTIVITY_START_DATE)
    @Json(name="start_date_local")
    val startDate: String? = "",

    @ColumnInfo(name = ActivityContract.Columns.ACTIVITY_ELAPSED_TIME)
    @Json(name="elapsed_time")
    val elapsedTime: Long,
    val distance: Double,
    val description: String? = ""
)

