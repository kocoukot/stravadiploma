package com.example.stravadiploma.useractivitylist

import android.app.Application
import androidx.lifecycle.*
import com.example.stravadiploma.data.ActivityData
import com.example.stravadiploma.data.UserForActivity
import com.example.stravadiploma.utils.SingleLiveEvent
import com.example.stravadiploma.utils.logInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserActivityViewModel(application: Application) : AndroidViewModel(application) {

    private val activityRepository = UserActivityRepository(application)

    private val _activityList = MutableLiveData<List<ActivityData>>()
    private val _userName = MutableLiveData<UserForActivity>()
    private val _isLoading = SingleLiveEvent<Boolean>()
    private val _isError = SingleLiveEvent<Boolean>()
    private val _updateList = MutableLiveData<Unit>()
    private val _isListEmpty = SingleLiveEvent<Boolean>()


    val activityList: LiveData<List<ActivityData>>
        get() = _activityList

    val isLoading: LiveData<Boolean>
        get() = _isLoading

    val isError: LiveData<Boolean>
        get() = _isError

    val userName: LiveData<UserForActivity>
        get() = _userName

    val isListEmpty: LiveData<Boolean>
        get() = _isListEmpty

    fun getAllActivities(isInternet: Boolean) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    _isError.postValue(false)
                    _isLoading.postValue(true)
                    val list = if (isInternet) {
                        activityRepository.getAllActivities()
                    } else {
                        activityRepository.getActivitiesFromRoom()
                    }

                    val user = activityRepository.getUserName()
                    _userName.postValue(user)
                    _activityList.postValue(list)
                    _updateList.postValue(Unit)
                    _isListEmpty.postValue(list.isEmpty())
                } catch (t: Throwable) {
                    _isError.postValue(true)
                    logInfo(t.localizedMessage)
                } finally {
                    _isLoading.postValue(false)
                }
            }
        }
    }

    fun addNewActivity(activity: ActivityData) {
        viewModelScope.launch {
            try {
                async {
                    val list = listOf(activity) + (_activityList.value ?: listOf())
                    activityRepository.startUploadToBD(activity)
                    _isListEmpty.postValue(list.isEmpty())
                    _activityList.postValue(list)
                }
                async { activityRepository.startUpload(activity) }
            } catch (t: Throwable) {
                _isError.postValue(true)
                logInfo(t.localizedMessage)
            }
        }
    }

    fun isUploaded() {
        activityRepository.isUploaded()
    }
}