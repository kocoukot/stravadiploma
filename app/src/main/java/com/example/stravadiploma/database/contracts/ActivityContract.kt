package com.example.stravadiploma.database.contracts

object ActivityContract {
    const val TABLE_NAME = "activity"

    object Columns{
        const val ACTIVITY_ID = "id"
        const val ACTIVITY_NAME = "activity_name"
        const val ACTIVITY_TYPE = "activity_type"
        const val ACTIVITY_START_DATE = "activity_start_date"
        const val ACTIVITY_ELAPSED_TIME = "activity_elapsed_time"
        const val ACTIVITY_DISTANCE = "activity_distance"
        const val ACTIVITY_DESCRIPTION = "activity_description"
    }
}