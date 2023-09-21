package com.example.nasadayimage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasadayimage.database.DayDataItemEntity
import com.example.nasadayimage.repository.DayDataItemLocalRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DayDataItemsLocallyViewModel(private val repository: DayDataItemLocalRepository) :
    ViewModel() {

    //    MutableLiveData for observing the fetched data from database
    val mutableLiveDataForFetchItems = MutableLiveData<List<DayDataItemEntity>>()

    //    Method for fetching the data from database
    fun fetchDataItemsLocally() {
        CoroutineScope(Dispatchers.IO).launch {
            mutableLiveDataForFetchItems.postValue(repository.fetchDataItemsLocally())
        }
    }

    //    Method for inserting the data to the database
    fun insertDataItemsLocally(dayDataItemEntity: DayDataItemEntity) =
        CoroutineScope(Dispatchers.IO).launch {
            repository.insertDayDataItemsLocally(dayDataItemEntity)
        }
}