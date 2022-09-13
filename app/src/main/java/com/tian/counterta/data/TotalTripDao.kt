package com.tian.counterta.data

import androidx.lifecycle.LiveData
import androidx.room.*
import com.tian.counterta.data.TotalTrip


@Dao
interface TotalTripDao {
    @Query("SELECT total_trip FROM TotalTrip WHERE number_card=:numberCard ")
    fun getTripById(numberCard : String) : String

    @Query("SELECT * FROM TotalTrip")
    fun getAllTrip() : LiveData<List<TotalTrip>>

    @Query("SELECT EXISTS(SELECT * FROM TotalTrip WHERE number_card=(:numberCard))")
    fun checkExistsNumberCard(numberCard: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg trips: TotalTrip)

    @Update
    fun updateUser(trip: TotalTrip?)

    @Query("DELETE FROM TotalTrip")
    fun deleteAllTrip()
}