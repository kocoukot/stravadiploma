package com.example.stravadiploma.newActivity

import android.app.Application
import androidx.lifecycle.*
import com.example.stravadiploma.data.ActivityData
import com.example.stravadiploma.utils.SingleLiveEvent
import com.example.stravadiploma.utils.logInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.ZonedDateTime

class NewActivityViewModel() : ViewModel() {

    private val _activityDuration = MutableLiveData<Int>()
    private val _activityDate = MutableLiveData<ZonedDateTime>()
    private val _isErrorAdding = SingleLiveEvent<Boolean>()
    private val _isSuccess = SingleLiveEvent<ActivityData>()
    private val _isErrorSaving = SingleLiveEvent<Boolean>()

    val activityDuration: LiveData<Int>
        get() = _activityDuration

    val activityDate: LiveData<ZonedDateTime>
        get() = _activityDate

    val isErrorAdding: LiveData<Boolean>
        get() = _isErrorAdding

    val isErrorSaving: LiveData<Boolean>
        get() = _isErrorSaving

    val isSuccess: LiveData<ActivityData>
        get() = _isSuccess

    fun saveNewActivity(
        activityName: String,
        activityType: String,
        activityDistance: Float,
        activityDescription: String
    ) {
        if (activityName.isNullOrEmpty() || activityType.isNullOrEmpty() || activityDistance.isNaN() || _activityDuration.value.toString()
                .isNullOrEmpty() || _activityDate.value.toString().isNullOrEmpty()
        ) {
            _isErrorAdding.postValue(true)
        } else {
            _isErrorAdding.postValue(false)
            viewModelScope.launch {
                withContext(Dispatchers.IO) {
                    try {
                        _isErrorSaving.postValue(false)
                        val newActivity = createNewActivity(
                            activityName,
                            activityType,
                            activityDistance,
                            activityDescription
                        )
                        _isSuccess.postValue(newActivity)
                    } catch (t: Throwable) {
                        _isErrorSaving.postValue(true)
                        logInfo(t)
                    }
                }
            }
        }
    }

    fun setDuration(hour: Int, minute: Int) {
        val duration = hour * 3600 + minute * 60
        _activityDuration.postValue(duration)
    }

    fun setDate(year: Int, month: Int, day: Int) {
        val date = LocalDateTime.of(
            year,
            month + 1,
            day,
            LocalDateTime.now().hour,
            LocalDateTime.now().minute,
            LocalDateTime.now().second
        )
            .atZone(ZoneOffset.UTC)
        logInfo(date)
        _activityDate.postValue(date)
    }

    private fun createNewActivity(
        activityName: String,
        activityType: String,
        activityDistance: Float,
        activityDescription: String
    ): ActivityData {
        val date =
            if (_activityDate.value == null) LocalDateTime.now()
                .toString() else _activityDate.value.toString()
        val description = if (activityDescription.isEmpty()) " " else activityDescription
        return ActivityData(
            0,
            activityName,
            activityType,
            date,
            _activityDuration.value!!,
            activityDistance,
            description
        )
    }
}