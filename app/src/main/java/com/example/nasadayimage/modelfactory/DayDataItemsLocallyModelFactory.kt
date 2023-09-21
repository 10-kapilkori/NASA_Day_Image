package com.example.nasadayimage.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasadayimage.repository.DayDataItemLocalRepository
import com.example.nasadayimage.viewmodel.DayDataItemsLocallyViewModel

//  View Model Factory Class to check and return the same View Model class which was assigned
class DayDataItemsLocallyModelFactory(private val repository: DayDataItemLocalRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DayDataItemsLocallyViewModel::class.java)) {
            return DayDataItemsLocallyViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")
    }
}