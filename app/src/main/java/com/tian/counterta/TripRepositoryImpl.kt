package com.tian.counterta

import com.tian.counterta.data.TotalTrip
import com.tian.counterta.data.TotalTripDao
import com.tian.counterta.data.TripDatabase
import javax.inject.Inject

class TripRepositoryImpl @Inject constructor(val tripDatabase: TripDatabase) {
    val allTrip: List<TotalTrip> = tripDatabase.getAllTrip()

    fun insert(trip: TotalTrip) {
        tripDao.insert(trip)
    }

    fun checkExistsNumberCard(numberCard: String) : Boolean {
        return tripDao.checkExistsNumberCard(numberCard)
    }
}