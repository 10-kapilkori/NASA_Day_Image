package com.example.nasadayimage.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.nasadayimage.pojos.DayDataItems
import com.example.nasadayimage.repository.DayDataItemRepository
import com.example.nasadayimage.retrofit.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DayDataItemViewModel(private val repo: DayDataItemRepository) : ViewModel() {

    //    MutableLiveData for observing the changes from API class
    val mutableLiveData = MutableLiveData<Resource<ArrayList<DayDataItems>>>()

    //    Method to fetch the data from the API call
    fun fetchDayDataItems(startDate: String, endDate: String) {

//    Executing the api in the IO thread
        CoroutineScope(Dispatchers.IO).launch {
            mutableLiveData.postValue(Resource.loading(null))

//            using a try-catch block for error handling
            try {
                mutableLiveData.postValue(
                    Resource.success(
                        repo.getDayDataItems(startDate, endDate).body()
                    )
                )
            } catch (e: Exception) {
                mutableLiveData.postValue(Resource.failure(e.message.toString(), null))
            }
        }
    }
}