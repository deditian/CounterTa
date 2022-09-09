package com.tian.counterta


import com.tian.counterta.data.TotalTrip
import com.tian.counterta.data.TotalTripDao

interface TripRepository {
    val allTrip: List<TotalTrip>
    fun insert(trip: TotalTrip)
    fun checkExistsNumberCard(numberCard: String) : Boolean
}