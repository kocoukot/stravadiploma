package com.example.stravadiploma.profile

import com.example.stravadiploma.data.UserProfile
import com.example.stravadiploma.database.Database
import com.example.stravadiploma.net.Network
import com.example.stravadiploma.net.SuccessAccessToken
import com.example.stravadiploma.utils.logInfo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ProfileRepository {

    private val userDao = Database.instance.userDao()

    suspend fun getUserProfile(): UserProfile {
        val user = Network.userApi.searchUsers()
        userDao.addUser(user)

        return user
    }

    fun setUserNewWeight(
        weight: Double,
        callback: (Boolean) -> Unit,
        errorCallBack: (e: Throwable) -> Unit
    ) {
        Network.userApi.setUserNewWeight(weight).enqueue(object : Callback<UserProfile> {
            override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                logInfo("Error response on check if stared or not stared")
                errorCallBack(t)
            }

            override fun onResponse(call: Call<UserProfile>, response: Response<UserProfile>) {
                if (response.isSuccessful) {
                    logInfo("Successfully uploaded")
                    callback(true)
                } else {
                    errorCallBack(Throwable("asd"))
                    logInfo("Error response on check if stared or not stared")
                }
            }
        })
    }

    fun logoutProfile(
        callback: (Boolean) -> Unit,
        errorCallBack: (e: Throwable) -> Unit
    ) {

        Network.userApi.logoutProfile().enqueue(object : Callback<Any> {
            override fun onResponse(call: Call<Any>, response: Response<Any>) {
                if (response.isSuccessful) {
                    SuccessAccessToken.token = ""
                    logInfo("Successfully uploaded")
                    callback(true)
                } else {
                    errorCallBack(Throwable("asd"))
                    logInfo("Error response on check if stared or not stared")
                }
            }

            override fun onFailure(call: Call<Any>, t: Throwable) {
                errorCallBack(t)
            }
        })
    }

    suspend fun deleteInfo(){
        userDao.deleteUser()
        userDao.deleteActivity()
    }
}