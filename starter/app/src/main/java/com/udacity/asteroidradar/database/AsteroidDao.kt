package com.udacity.asteroidradar.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.udacity.asteroidradar.entity.AsteroidData

@Dao
interface AsteroidDao {

    @Insert
    fun insert(asteroids: List<AsteroidData>)

    @Query("SELECT * FROM asteroid WHERE closeApproachDate >= :todayDate ORDER BY closeApproachDate ASC")
    fun getAsteroids(todayDate: String): LiveData<List<AsteroidData>>

    @Query("DELETE FROM asteroid WHERE closeApproachDate < :todayDate")
    suspend fun clearOutdatedData(todayDate: String)
}