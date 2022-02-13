package com.udacity.asteroidradar.main

import androidx.lifecycle.*
import com.udacity.asteroidradar.Constants.NASA_API_KEY
import com.udacity.asteroidradar.api.getDateToday
import com.udacity.asteroidradar.network.NasaApi
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.uimodel.Asteroid
import com.udacity.asteroidradar.uimodel.AsteroidMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val asteroidRepository: AsteroidRepository) : ViewModel() {

    private val _pictureOfTheDayUrl = MutableLiveData<String>()
    val pictureOfTheDayUrl: LiveData<String>
        get() = _pictureOfTheDayUrl

    val asteroids: LiveData<List<Asteroid>> =
        Transformations.map(asteroidRepository.observeAsteroids(getDateToday())) {
            AsteroidMapper().mapToUi(it)
        }


    init {
        viewModelScope.launch {
            getPictureOfTheDay()
            refreshAsteroids()
        }
    }

    private suspend fun getPictureOfTheDay() {
        runCatching {
            withContext(Dispatchers.IO) { NasaApi.retrofitService.getPictureOfTheDay(apiKey = NASA_API_KEY) }
        }.onSuccess { pictureOfTheDay ->
            _pictureOfTheDayUrl.value = pictureOfTheDay.url
        }
    }

    private suspend fun refreshAsteroids() {
        runCatching {
            withContext(Dispatchers.IO) { asteroidRepository.refreshAsteroids() }
        }
    }
}