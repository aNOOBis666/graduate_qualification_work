package com.codehunters.local_storage.interfaces

import com.codehunters.local_storage.models.GlucoseInfo
import kotlinx.coroutines.flow.Flow

interface IGlucoseInterface {
    suspend fun add(item: GlucoseInfo)
    suspend fun addList(items: List<GlucoseInfo>)
    suspend fun deleteById(id: String)
    suspend fun getAll(): List<GlucoseInfo>
    suspend fun getById(id: String): GlucoseInfo
    fun getAllFlow(): Flow<List<GlucoseInfo>>
}