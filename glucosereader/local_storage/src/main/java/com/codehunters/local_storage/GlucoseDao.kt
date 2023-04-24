package com.codehunters.local_storage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

/**
 * GlucoseDao is a class that contains database query bodies
 * @param add - creates new glucose value in table
 * @param addList - creates list of new glucose values in table
 * @param deleteById - removes incorrect glucose value from table
 * @param getAll - gets list of glucose values
 * @param getById - get concrete glucose value by its id
 * @param getAllFlow - gets subscription on changing values and returns async list of values
 */
@Dao
interface GlucoseDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun add(item: GlucoseEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addList(items: List<GlucoseEntity>)

    @Query("delete from glucose where _id=:id")
    suspend fun deleteById(id: String)

    @Query("select * from glucose")
    suspend fun getAll(): List<GlucoseEntity>

    @Query("select * from glucose where _id=:id")
    suspend fun getById(id: String): GlucoseEntity

    @Query("select * from glucose")
    fun getAllFlow(): Flow<List<GlucoseEntity>>
}