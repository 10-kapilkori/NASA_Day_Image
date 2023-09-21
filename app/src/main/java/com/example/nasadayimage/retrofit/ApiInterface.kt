package com.example.nasadayimage.retrofit

import com.example.nasadayimage.pojos.DayDataItems
import com.example.nasadayimage.staticmethods.Constants.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("/planetary/apod?api_key=$API_KEY")
    suspend fun getNasaDayImageData(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String
    ): Response<ArrayList<DayDataItems>>
}