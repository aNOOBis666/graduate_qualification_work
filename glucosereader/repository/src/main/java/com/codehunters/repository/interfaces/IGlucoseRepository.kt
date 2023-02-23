package com.codehunters.repository.interfaces

import com.codehunters.data.models.GlucoseData
import kotlinx.coroutines.flow.Flow

interface IGlucoseRepository {
    suspend fun add(item: GlucoseData)
    suspend fun addList(items: List<GlucoseData>)
    suspend fun deleteById(id: String)
    suspend fun getAll(): List<GlucoseData>
    suspend fun getById(id: String): GlucoseData
    fun getAllFlow(): Flow<List<GlucoseData>>
}