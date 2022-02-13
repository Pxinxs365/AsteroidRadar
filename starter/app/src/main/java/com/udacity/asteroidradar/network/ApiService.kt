package com.udacity.asteroidradar.network

import com.facebook.stetho.okhttp3.StethoInterceptor
import com.google.gson.JsonObject
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.udacity.asteroidradar.BuildConfig
import com.udacity.asteroidradar.Constants.BASE_URL
import com.udacity.asteroidradar.entity.PictureOfTheDay
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

private const val DEBUG_CONST = "debug"
private const val TIMEOUT = 20L

private val okHttpClient = OkHttpClient.Builder().apply {
    if (BuildConfig.BUILD_TYPE == DEBUG_CONST) {
        addNetworkInterceptor(StethoInterceptor())
    }
    readTimeout(TIMEOUT, TimeUnit.SECONDS)
    writeTimeout(TIMEOUT, TimeUnit.SECONDS)
    connectTimeout(TIMEOUT, TimeUnit.SECONDS)
}.build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .client(okHttpClient)
    .build()

interface NasaApiService {
    @GET("neo/rest/v1/feed")
    fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Call<JsonObject>

    @GET("planetary/apod")
    suspend fun getPictureOfTheDay(@Query("api_key") apiKey: String): PictureOfTheDay
}

object NasaApi {
    val retrofitService: NasaApiService by lazy {
        retrofit.create(NasaApiService::class.java)
    }
}