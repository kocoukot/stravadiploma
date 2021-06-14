package com.example.stravadiploma.profile

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stravadiploma.data.UserProfile
import com.example.stravadiploma.utils.LogInfo
import com.example.stravadiploma.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ProfileViewModel : ViewModel() {

    private val profileRepository = ProfileRepository()

    private val userProfileLiveData = MutableLiveData<UserProfile>()


    private val isLoadingLiveData = MutableLiveData<Boolean>()
    private val isErrorLiveData = MutableLiveData<Boolean>()

    private val isProfileLoadedLiveData = SingleLiveEvent<Boolean>()
    private val isProfileLogoutLiveData = SingleLiveEvent<Unit>()

    val userProfile: LiveData<UserProfile>
        get() = userProfileLiveData


    val isLoading: LiveData<Boolean>
        get() = isLoadingLiveData

    val isError: LiveData<Boolean>
        get() = isErrorLiveData

    val isProfileLoaded: LiveData<Boolean>
        get() = isProfileLoadedLiveData

    val isProfileLogout: LiveData<Unit>
        get() = isProfileLogoutLiveData


    fun getUserProfile() {
        viewModelScope.launch {
            try {
                isProfileLoadedLiveData.postValue(false)
                isLoadingLiveData.postValue(true)
                isErrorLiveData.postValue(false)
                val user = profileRepository.getUserProfile()
                userProfileLiveData.postValue(user)
                isLoadingLiveData.postValue(false)
                isProfileLoadedLiveData.postValue(true)
            } catch (t: Throwable) {
                Log.d("DiplomaProject", t.localizedMessage!!)
                isLoadingLiveData.postValue(false)
                isErrorLiveData.postValue(true)
            }
        }
    }

    fun setUserNewWeight(weight: Double) {
        viewModelScope.launch {
            try {
                profileRepository.setUserNewWeight(weight, {
                    isLoadingLiveData.postValue(false)
                }, {
                    isLoadingLiveData.postValue(false)
                    isErrorLiveData.postValue(true)
                })
            } catch (t: Throwable) {
                isErrorLiveData.postValue(true)
                LogInfo(t.localizedMessage!!)
            }
        }
    }

    fun logoutProfile() {
        viewModelScope.launch {
            try {
                profileRepository.logoutProfile({
                    LogInfo("logged out")
                    isProfileLogoutLiveData.postValue(Unit)
                },{
                    LogInfo("error logged out")
                })

            } catch (t: Throwable) {
             //   isErrorLiveData.postValue(true)
                LogInfo(t.localizedMessage!!)
            }
        }
    }

    fun getWeightList(): List<Int> {
        val items = mutableListOf<Int>()
        for (i in 50..350) {
            items.add(i)
        }
        return items
    }
}