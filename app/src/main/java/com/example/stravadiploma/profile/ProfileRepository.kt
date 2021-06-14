package com.example.stravadiploma.profile

import android.util.Log
import com.example.stravadiploma.data.UserProfile
import com.example.stravadiploma.net.Network
import com.example.stravadiploma.utils.LogInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Error
import java.util.*

class ProfileRepository {

    suspend fun getUserProfile(): UserProfile {
        return Network.githubApi.searchUsers()

    }

    fun setUserNewWeight(
        weight: Double,
        callback: (Boolean) -> Unit,
        errorCallBack: (e: Throwable) -> Unit
    ) {
        Network.githubApi.setUserNewWeight(weight).enqueue(object : Callback<UserProfile> {
            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                LogInfo("Error response on check if stared or not stared")
                errorCallBack(t)
            }

            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                if (response.isSuccessful) {
                    LogInfo("Successfully uploaded")
                    callback(true)
                } else {
                    errorCallBack(Throwable("asd"))
                    LogInfo("Error response on check if stared or not stared")
                }
            }
        })
    }

    fun logoutProfile(
        callback: (Boolean) -> Unit,
        errorCallBack: (e: Throwable) -> Unit
    ) {
        Network.githubApi.logoutProfile().enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    LogInfo("Successfully uploaded")
                    callback(true)
                } else {
                    errorCallBack(Throwable("asd"))
                    LogInfo("Error response on check if stared or not stared")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                errorCallBack(t)
            }
        })
    }
}