package com.codehunters.local_storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface GlucoseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: GlucoseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(items: List<GlucoseEntity>)

    @Query("delete from glucose where _id=:id")
    suspend fun deleteById(id: String, type: String)

    @Query("select * from glucose")
    suspend fun getAll(): List<GlucoseEntity>

    @Query("select * from glucose where _id=:id")
    suspend fun getById(id: String): GlucoseEntity

    @Query("select * from glucose")
    fun getAllFlow(): Flow<List<GlucoseEntity>>
}