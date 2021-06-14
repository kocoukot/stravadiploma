package com.example.stravadiploma.net

import com.example.stravadiploma.data.UserProfile
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.*
import java.util.*

interface UserProfileApi {
    @GET("api/v3/athlete")
    suspend fun searchUsers(): UserProfile

    @PUT("api/v3/athlete")
    fun setUserNewWeight(
        @Query("weight") weight: Double
    ): Call<UserProfile>

    @POST("oauth/deauthorize")
    fun logoutProfile(): Call<Any>
}