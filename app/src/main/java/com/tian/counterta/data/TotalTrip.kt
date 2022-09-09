package com.tian.counterta.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TotalTrip (
    @PrimaryKey
    @ColumnInfo(name = "number_card") val numberCard: String,
    @ColumnInfo(name = "total_trip") val trip: String
)