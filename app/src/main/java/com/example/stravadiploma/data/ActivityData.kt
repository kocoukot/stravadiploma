package com.example.stravadiploma.data

import androidx.room.*
import com.example.stravadiploma.database.contracts.ActivityContract
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity(
    tableName = ActivityContract.TABLE_NAME,
    primaryKeys = ["name", ActivityContract.Columns.ACTIVITY_START_DATE]
)

@JsonClass(generateAdapter = true)
data class ActivityData(

    @ColumnInfo()
    val id: Long,
    val name: String,
    val type: String,

    @ColumnInfo(name = ActivityContract.Columns.ACTIVITY_START_DATE)
    @Json(name="start_date_local")
    val startDate: String,

    @ColumnInfo(name = ActivityContract.Columns.ACTIVITY_ELAPSED_TIME)
    @Json(name="elapsed_time")
    val elapsedTime: Int,
    val distance: Float,
    val description: String? = ""
)

