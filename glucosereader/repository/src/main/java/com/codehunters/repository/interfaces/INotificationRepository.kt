package com.codehunters.repository.interfaces

import kotlinx.coroutines.flow.Flow

interface INotificationRepository {
    val isNotificationFlow: Flow<Boolean>
    val notificationFlow: Flow<Int>
    suspend fun setNotificationInterval(value: Int)
}