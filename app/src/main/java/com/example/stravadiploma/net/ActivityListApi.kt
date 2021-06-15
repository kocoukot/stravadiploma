package com.example.stravadiploma.net

import com.example.stravadiploma.data.ActivityData
import com.example.stravadiploma.data.UserProfile
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query

interface ActivityListApi {

    @GET("api/v3/athlete/activities")
    suspend fun getAllActivities(): List<ActivityData>

    @POST("api/v3/activities")
    fun setUserNewWeight(
        @Query("weight") weight: Double
    ): Call<UserProfile>


}