package com.codehunters.repository

import com.codehunters.data.models.GlucoseData
import com.codehunters.local_storage.interfaces.IGlucoseInterface
import com.codehunters.repository.interfaces.IGlucoseRepository
import com.codehunters.repository.mapping.toData
import com.codehunters.repository.mapping.toDataList
import com.codehunters.repository.mapping.toInfo
import com.codehunters.repository.mapping.toInfoList
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GlucoseRepository(
    private val glucoseInterface: IGlucoseInterface
) : IGlucoseRepository {
    override suspend fun add(item: GlucoseData) {
        glucoseInterface.add(item.toInfo())
    }

    override suspend fun addList(items: List<GlucoseData>) {
        glucoseInterface.addList(items.toInfoList())
    }

    override suspend fun deleteById(id: String) {
        glucoseInterface.deleteById(id)
    }

    override suspend fun getAll(): List<GlucoseData> {
        return glucoseInterface.getAll().toDataList()
    }

    override suspend fun getById(id: String): GlucoseData {
        return glucoseInterface.getById(id).toData()
    }

    override fun getAllFlow(): Flow<List<GlucoseData>> {
        return glucoseInterface.getAllFlow().map { it.toDataList() }
    }
}