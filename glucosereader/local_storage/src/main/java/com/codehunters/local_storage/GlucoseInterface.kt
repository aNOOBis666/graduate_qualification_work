package com.codehunters.local_storage

import com.codehunters.local_storage.interfaces.IGlucoseInterface
import com.codehunters.local_storage.mapping.toEntity
import com.codehunters.local_storage.mapping.toEntityList
import com.codehunters.local_storage.mapping.toInfo
import com.codehunters.local_storage.mapping.toInfoList
import com.codehunters.local_storage.models.GlucoseInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GlucoseInterface(
    private val db: GlucoseDatabase
) : IGlucoseInterface {
    override suspend fun add(item: GlucoseInfo) {
        db.glucoseDao.add(item.toEntity())
    }

    override suspend fun addList(items: List<GlucoseInfo>) {
        db.glucoseDao.addList(items.toEntityList())
    }

    override suspend fun deleteById(id: String) {
        db.glucoseDao.deleteById(id)
    }

    override suspend fun getAll(): List<GlucoseInfo> {
        return db.glucoseDao.getAll().toInfoList()
    }

    override suspend fun getById(id: String): GlucoseInfo {
        return db.glucoseDao.getById(id).toInfo()
    }

    override fun getAllFlow(): Flow<List<GlucoseInfo>> {
        return db.glucoseDao.getAllFlow().map { list ->
            list.toInfoList()
        }
    }
}