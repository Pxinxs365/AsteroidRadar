package com.udacity.asteroidradar.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "asteroid")
data class AsteroidData(
    @PrimaryKey
    val id: Long,
    val codename: String,
    val closeApproachDate: String,
    val absoluteMagnitude: Double,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean
)