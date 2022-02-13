package com.udacity.asteroidradar.entity

import com.google.gson.annotations.SerializedName

data class PictureOfTheDay(
    @SerializedName("media_type")
    val mediaType: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("url")
    val url: String
)