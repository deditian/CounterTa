package com.tian.counterta

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tian.counterta.data.TotalTrip
import kotlinx.coroutines.launch

class TripViewModel (private var repository: TripRepository): ViewModel() {

    val allTrip : List<TotalTrip> = repository.allTrip

    val _checkExistsNumberCard = MutableLiveData<Boolean>()

    fun checkExistsNumberCard(cardnumber : String) = viewModelScope.launch {
        _checkExistsNumberCard.postValue(repository.checkExistsNumberCard(cardnumber))
    }

    fun insert(tripTotal :TotalTrip) = viewModelScope.launch {
        repository.insert(tripTotal)
    }
}