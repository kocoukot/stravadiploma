package com.example.stravadiploma.net

import android.content.Context
import android.os.Debug
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

import com.example.stravadiploma.utils.logInfo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Query
import kotlin.math.log

class UploadWorker(
    private val context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        val name = inputData.getString(NAME) ?: ""
        val type = inputData.getString(TYPE) ?: ""
        val startDate = inputData.getString(START) ?: ""
        val elapsedTime = inputData.getInt(TIME, 0)
        val description = inputData.getString(DESC) ?: ""
        val distance = inputData.getFloat(DIST, 0.toFloat())

        return uploadActivity(
            name,
            type,
            startDate,
            elapsedTime,
            description,
            distance
        )
    }

    private suspend fun uploadActivity(
        name: String,
        type: String,
        startDate: String,
        elapsedTime: Int,
        description: String,
        distance: Float,
        ): Result {
        return try {
            Network.activityApi.createNewActivity(
                name,
                type,
                startDate,
                elapsedTime,
                description,
                distance,
                0, 0
            )
                            Result.success()

        } catch (t: Throwable) {
            logInfo(t.localizedMessage)
            Result.retry()
        }
    }


    companion object {
        const val NAME = "name"
        const val TYPE = "type"
        const val START = "start"
        const val TIME = "time"
        const val DESC = "desc"
        const val DIST = "disc"
    }

}