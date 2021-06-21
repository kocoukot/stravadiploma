package com.example.stravadiploma.net

import com.example.stravadiploma.data.ActivityData
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ActivityListApi {

    @GET("api/v3/athlete/activities")
    suspend fun getAllActivities(): List<ActivityData>

    @POST("api/v3/activities")
    suspend fun createNewActivity(
        @Query("name") name: String,
        @Query("type") type: String,
        @Query("start_date_local") startDate: String,
        @Query("elapsed_time") elapsedTime: Int,
        @Query("description") description: String,
        @Query("distance") distance: Float,
        @Query("trainer") trainer: Int,
        @Query("commute") commute: Int,
    )
}