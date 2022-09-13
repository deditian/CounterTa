package com.tian.counterta


import androidx.lifecycle.LiveData
import com.tian.counterta.data.TotalTrip
import com.tian.counterta.data.TotalTripDao

class TripRepository (val tripDao: TotalTripDao) {
    val allTrip: LiveData<List<TotalTrip>> = tripDao.getAllTrip()

    fun getTripById(cardNumber:String): String {
        return tripDao.getTripById(cardNumber)
    }

    fun insert(trip: TotalTrip) {
        tripDao.insert(trip)
    }

    fun checkExistsNumberCard(numberCard: String) : Boolean {
        return tripDao.checkExistsNumberCard(numberCard)
    }
}