package com.example.nasadayimage.pojos

import com.google.gson.annotations.SerializedName

// Data class for the fetching the details from API response
data class DayDataItems(
    @SerializedName("date")
    var date: String? = null,
    @SerializedName("explanation")
    var description: String? = null,
    @SerializedName("hdurl")
    var imgUrl: String? = null,
    @SerializedName("media_type")
    var mediaType: String? = null,
    @SerializedName("title")
    var imgTitle: String? = null,
    @SerializedName("url")
    var url: String? = null
)