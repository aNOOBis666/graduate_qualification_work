package com.codehunters.local_storage

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GlucoseEntity::class], version = 1, exportSchema = false)
abstract class GlucoseDatabase : RoomDatabase() {
    abstract val glucoseDao: GlucoseDao
}