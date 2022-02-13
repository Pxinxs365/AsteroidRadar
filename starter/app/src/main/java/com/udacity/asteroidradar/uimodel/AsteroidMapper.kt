package com.udacity.asteroidradar.uimodel

import com.udacity.asteroidradar.entity.AsteroidData

class AsteroidMapper {

    fun mapToUi(asteroidsData: List<AsteroidData>): List<Asteroid> {
        return asteroidsData.map { asteroidData ->
            Asteroid(
                id = asteroidData.id,
                codename = asteroidData.codename,
                closeApproachDate = asteroidData.closeApproachDate,
                absoluteMagnitude = asteroidData.absoluteMagnitude,
                estimatedDiameter = asteroidData.estimatedDiameter,
                relativeVelocity = asteroidData.relativeVelocity,
                distanceFromEarth = asteroidData.distanceFromEarth,
                isPotentiallyHazardous = asteroidData.isPotentiallyHazardous
            )
        }
    }

    fun map(asteroids: List<Asteroid>): List<AsteroidData> {
        return asteroids.map { asteroid ->
            AsteroidData(
                id = asteroid.id,
                codename = asteroid.codename,
                closeApproachDate = asteroid.closeApproachDate,
                absoluteMagnitude = asteroid.absoluteMagnitude,
                estimatedDiameter = asteroid.estimatedDiameter,
                relativeVelocity = asteroid.relativeVelocity,
                distanceFromEarth = asteroid.distanceFromEarth,
                isPotentiallyHazardous = asteroid.isPotentiallyHazardous
            )
        }
    }
}