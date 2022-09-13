package com.tian.counterta.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tian.counterta.data.TotalTrip
import com.tian.counterta.data.TotalTripDao

@Database(entities = [TotalTrip::class], version = 1, exportSchema = false)
abstract class TripDatabase: RoomDatabase() {
    abstract fun tripDao(): TotalTripDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: TripDatabase? = null

        fun getDatabase(context: Context): TripDatabase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    TripDatabase::class.java,
                    "trip-db"
                ).build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }
}