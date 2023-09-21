package com.example.nasadayimage.database

import androidx.room.Entity
import androidx.room.PrimaryKey

// Created a table for the local database
@Entity(tableName = "NasaDayDataItem")
data class DayDataItemEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var title: String,
    var imgUrl: String,
    var description: String,
    var mediaType: String,
    var postedDate: String
)