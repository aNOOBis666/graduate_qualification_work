package com.codehunters.local_storage

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "glucose")
data class GlucoseEntity(
    @PrimaryKey(autoGenerate = true)
    val _id: Int = 0,
    val value: Float,
    val creationDate: Long
)
