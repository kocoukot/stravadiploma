package com.example.stravadiploma.profile

import android.app.Application
import androidx.lifecycle.*
import com.example.stravadiploma.data.UserProfile
import com.example.stravadiploma.utils.logInfo
import com.example.stravadiploma.utils.SingleLiveEvent
import kotlinx.coroutines.launch

class ProfileViewModel (application: Application) : AndroidViewModel(application){

    private val profileRepository = ProfileRepository(application)

    private val _userProfile = MutableLiveData<UserProfile>()

    private val _isLoading = MutableLiveData<Boolean>()
    private val _isError = MutableLiveData<Boolean>()

    private val isProfileLogoutLiveData = SingleLiveEvent<Unit>()

    val userProfile: LiveData<UserProfile>
        get() = _userProfile

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isProfileLogout: LiveData<Unit>
        get() = isProfileLogoutLiveData


    fun getUserProfile() {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                _isError.postValue(false)
                val user = profileRepository.getUserProfile()
                _userProfile.postValue(user)
                _isLoading.postValue(false)
            } catch (t: Throwable) {
                logInfo(t.localizedMessage)
                _isLoading.postValue(false)
                _isError.postValue(true)
            }
        }
    }

    fun setUserNewWeight(weight: Double) {
        viewModelScope.launch {
            try {
                _isLoading.postValue(true)
                profileRepository.setUserNewWeight(weight, {
                    _isLoading.postValue(false)
                }, {
                    _isLoading.postValue(false)
                    _isError.postValue(true)
                })
            } catch (t: Throwable) {
                _isError.postValue(true)
                logInfo(t.localizedMessage)
            }
        }
    }

    fun logoutProfile() {
        viewModelScope.launch {
            try {
                profileRepository.logoutProfile({
                    logInfo("Logged out")
                    isProfileLogoutLiveData.postValue(Unit)
                }, {
                    logInfo("Error logged out")
                })
                profileRepository.deleteInfo()
            } catch (t: Throwable) {
                //   isErrorLiveData.postValue(true)
                logInfo(t.localizedMessage!!)
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