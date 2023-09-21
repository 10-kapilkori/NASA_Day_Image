package com.example.nasadayimage.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.nasadayimage.repository.DayDataItemRepository
import com.example.nasadayimage.viewmodel.DayDataItemViewModel

//  View Model Factory Class to check and return the same View Model class which was assigned
class DayDataItemModelFactory(private val repo: DayDataItemRepository) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DayDataItemViewModel::class.java)) {
            return DayDataItemViewModel(repo) as T
        }

        throw IllegalArgumentException("Unknown View Model Class")
    }
}