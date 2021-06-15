package com.example.stravadiploma.useractivitylist

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.stravadiploma.data.ActivityData
import com.example.stravadiploma.data.UserForActivity
import com.example.stravadiploma.utils.SingleLiveEvent
import com.example.stravadiploma.utils.logInfo
import kotlinx.coroutines.launch

class UserActivityViewModel : ViewModel() {

    private val activityRepository = UserActivityRepository()

    private val _userName = SingleLiveEvent<UserForActivity>()
    private val _activityList = MutableLiveData<List<ActivityData>>()
    private val _isLoading = SingleLiveEvent<Boolean>()
    private val _isError = SingleLiveEvent<Boolean>()

    val activityList: LiveData<List<ActivityData>>
        get() = _activityList

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isError: LiveData<Boolean>
        get() = _isError

    val userName: LiveData<UserForActivity>
        get() = _userName

    fun getAllActivities(){
        logInfo("making request")
        viewModelScope.launch {
            try {
                _isError.postValue(false)
                _isLoading.postValue(true)
                val list = activityRepository.getAllActivities()
                _activityList.postValue(list)
            } catch (t:Throwable){
                _isError.postValue(true)
                logInfo(t.localizedMessage)
            } finally {
                _isLoading.postValue(false)
            }
        }
    }

    fun getUserName (){
        viewModelScope.launch {
            try {
                val test = activityRepository.getUserName()
                _userName.postValue(test)
            } catch (t:Throwable){
                _isError.postValue(true)
                logInfo(t.localizedMessage)
            }
        }
    }


}