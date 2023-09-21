package com.example.nasadayimage.repository

import com.example.nasadayimage.database.DAO
import com.example.nasadayimage.database.DayDataItemEntity

// A repository class for the Database to fetch and insert data items in the database
class DayDataItemLocalRepository(private val dao: DAO) {

    suspend fun insertDayDataItemsLocally(items: DayDataItemEntity) = dao.insertDataItems(items)

    suspend fun fetchDataItemsLocally() = dao.fetchDataItemsLocally()
}