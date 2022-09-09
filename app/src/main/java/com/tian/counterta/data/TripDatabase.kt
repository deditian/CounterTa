package com.tian.counterta.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tian.counterta.data.TotalTrip
import com.tian.counterta.data.TotalTripDao

@Database(entities = [TotalTrip::class], version = 1)
abstract class TripDatabase: RoomDatabase() {
    abstract fun tripDao(): TotalTripDao
}