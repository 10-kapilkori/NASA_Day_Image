package com.example.nasadayimage.repository

import com.example.nasadayimage.retrofit.ApiInterface

// A repository class for the Database to fetch data items from server
class DayDataItemRepository(private val api: ApiInterface) {

    //    "startDate" and "endDate" is used to fetch the details between those certain dates
    suspend fun getDayDataItems(startDate: String, endDate: String) =
        api.getNasaDayImageData(startDate, endDate)
}