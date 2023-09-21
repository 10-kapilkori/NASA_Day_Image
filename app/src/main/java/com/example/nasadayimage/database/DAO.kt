package com.example.nasadayimage.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DAO {

    //    Method to add data into the database
    @Insert
    suspend fun insertDataItems(dayDataItemEntity: DayDataItemEntity)

    //    Method to fetch data from the database in the descending order to show the latest one at top
    @Query("SELECT * FROM NasaDayDataItem ORDER BY postedDate DESC")
    suspend fun fetchDataItemsLocally(): List<DayDataItemEntity>
}