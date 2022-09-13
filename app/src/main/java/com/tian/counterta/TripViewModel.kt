package com.tian.counterta

import android.app.Application
import androidx.lifecycle.*
import com.tian.counterta.data.TotalTrip
import com.tian.counterta.data.TripDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TripViewModel(application: Application) : AndroidViewModel(application) {

    val repository: TripRepository
    val allTrip : LiveData<List<TotalTrip>>

    init {
        val dao = TripDatabase.getDatabase(application).tripDao()
        repository = TripRepository(dao)
        allTrip = repository.allTrip
    }

    val _getTripById =  MutableLiveData<String>()
    fun getTripById(cardnumber : String) = viewModelScope.launch(Dispatchers.IO) {
        _getTripById.postValue(repository.getTripById(cardnumber))
    }

    val _checkExistsNumberCard = MutableLiveData<Boolean>()
    fun checkExistsNumberCard(cardnumber : String) = viewModelScope.launch(Dispatchers.IO) {
        _checkExistsNumberCard.postValue(repository.checkExistsNumberCard(cardnumber))
    }

    fun insert(tripTotal :TotalTrip) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(tripTotal)
    }
}