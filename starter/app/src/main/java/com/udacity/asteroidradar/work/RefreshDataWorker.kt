package com.udacity.asteroidradar.work

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.udacity.asteroidradar.api.getDateToday
import com.udacity.asteroidradar.database.AppDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class RefreshDataWorker(private val appContext: Context, params: WorkerParameters) :
    CoroutineWorker(appContext, params) {

    override suspend fun doWork(): Result {
        val asteroidDao = AppDatabase.getInstance(appContext).asteroidDao
        val asteroidRepository = AsteroidRepository.getInstance(asteroidDao)

        return withContext(Dispatchers.IO) {
            try {
                asteroidRepository.refreshAsteroids()
                asteroidRepository.removeOutdatedAsteroids(getDateToday())
                Result.success()
            } catch (e: HttpException) {
                Result.retry()
            }
        }
    }

    companion object {
        const val WORKER_NAME = "refresh_data_worker"
    }
}