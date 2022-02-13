package com.udacity.asteroidradar.repository

import androidx.lifecycle.LiveData
import com.udacity.asteroidradar.Constants
import com.udacity.asteroidradar.api.getDateTo
import com.udacity.asteroidradar.api.getDateToday
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDao
import com.udacity.asteroidradar.entity.AsteroidData
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.uimodel.AsteroidMapper
import org.json.JSONObject
import retrofit2.await

class AsteroidRepository(private val asteroidDao: AsteroidDao) {

    fun observeAsteroids(todayDate: String): LiveData<List<AsteroidData>> =
        asteroidDao.getAsteroids(todayDate)

    suspend fun refreshAsteroids() {
        runCatching {
            val response = NasaApi.retrofitService.getAsteroids(
                startDate = getDateToday(),
                endDate = getDateTo(),
                apiKey = Constants.NASA_API_KEY
            ).await()
            parseAsteroidsJsonResult(JSONObject(response.toString()))
        }.onSuccess { asteroids ->
            saveAsteroidsToDatabase(AsteroidMapper().map(asteroids))
        }
    }

    private fun saveAsteroidsToDatabase(asteroids: List<AsteroidData>) {
        asteroidDao.insert(asteroids)
    }

    suspend fun removeOutdatedAsteroids(todayDate: String) {
        asteroidDao.clearOutdatedData(todayDate)
    }

    companion object {
        @Volatile
        private var INSTANCE: AsteroidRepository? = null

        fun getInstance(asteroidDao: AsteroidDao): AsteroidRepository {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = AsteroidRepository(asteroidDao)
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}