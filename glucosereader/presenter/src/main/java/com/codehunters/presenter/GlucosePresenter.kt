package com.codehunters.presenter

import com.codehunters.data.models.GlucoseData
import com.codehunters.presenter.interfaces.IGlucosePresenter
import com.codehunters.repository.interfaces.IGlucoseRepository
import kotlinx.coroutines.flow.Flow

class GlucosePresenter(
    private val glucoseRepository: IGlucoseRepository
): IGlucosePresenter {
    override suspend fun add(item: GlucoseData) {
        glucoseRepository.add(item)
    }
    override suspend fun addList(items: List<GlucoseData>) {
        glucoseRepository.addList(items)
    }
    override suspend fun deleteById(id: String) {
        glucoseRepository.deleteById(id)
    }
    override suspend fun getAll(): List<GlucoseData> {
        return glucoseRepository.getAll()
    }
    override suspend fun getById(id: String): GlucoseData {
        return glucoseRepository.getById(id)
    }
    override fun getAllFlow(): Flow<List<GlucoseData>> {
        return glucoseRepository.getAllFlow()
    }
}