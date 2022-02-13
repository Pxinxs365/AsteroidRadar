package com.udacity.asteroidradar

import android.app.Application
import androidx.work.*
import com.facebook.stetho.Stetho
import com.udacity.asteroidradar.work.RefreshDataWorker
import com.udacity.asteroidradar.work.RefreshDataWorker.Companion.WORKER_NAME
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class App : Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        Stetho.initializeWithDefaults(this)
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRefreshDataWorker()
        }
    }

    private fun setupRefreshDataWorker() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .setRequiresBatteryNotLow(true)
            .setRequiresCharging(true)
            .build()
        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshDataWorker>(
            repeatInterval = 1,
            repeatIntervalTimeUnit = TimeUnit.DAYS
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance()
            .enqueueUniquePeriodicWork(
                WORKER_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest
            )
    }
}