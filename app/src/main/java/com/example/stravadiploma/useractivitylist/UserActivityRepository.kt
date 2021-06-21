package com.example.stravadiploma.useractivitylist

import android.content.Context
import androidx.work.*
import com.example.stravadiploma.data.ActivityData
import com.example.stravadiploma.data.UserForActivity
import com.example.stravadiploma.database.Database
import com.example.stravadiploma.net.Network
import com.example.stravadiploma.net.UploadWorker
import com.example.stravadiploma.utils.logInfo
import java.util.concurrent.TimeUnit

class UserActivityRepository(
    private val context: Context
) {

    private val activityDao = Database.instance.activityDao()

    suspend fun getAllActivities(): List<ActivityData> {
        val activityList = Network.activityApi.getAllActivities()
        activityDao.addActivity(activityList)
        return activityList
    }

    suspend fun getUserName(): UserForActivity {
        return activityDao.getUserName()
    }

    suspend fun getActivitiesFromRoom(): List<ActivityData> {
        return activityDao.getAllActivities()
    }

    fun startUpload(activityData: ActivityData) {

        val workData = workDataOf(
            UploadWorker.NAME to activityData.name,
            UploadWorker.START to activityData.startDate,
            UploadWorker.TYPE to activityData.type,
            UploadWorker.START to activityData.startDate,
            UploadWorker.TIME to activityData.elapsedTime,
            UploadWorker.DESC to activityData.description,
            UploadWorker.DIST to activityData.distance
        )

        val workConstraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED).build()

        val workRequest = OneTimeWorkRequestBuilder<UploadWorker>()
            .setBackoffCriteria(BackoffPolicy.LINEAR, 10, TimeUnit.SECONDS)
            .setInputData(workData)
            .setConstraints(workConstraints)
            .build()

        WorkManager.getInstance(context)
            .enqueueUniqueWork(UPLOAD_KEY, ExistingWorkPolicy.APPEND, workRequest)
    }

    suspend fun startUploadToBD(activityData: ActivityData) {
        activityDao.addActivity(listOf(activityData))
    }

    fun isUploaded() {
        WorkManager.getInstance(context).cancelUniqueWork(UPLOAD_KEY)
    }

    companion object {
        const val UPLOAD_KEY = "upload_key"
    }

}