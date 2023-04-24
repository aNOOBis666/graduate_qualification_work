package com.codehunters.local_storage.interfaces

import kotlinx.coroutines.flow.Flow

interface ISharedPreferencesManager {
    val isNotificationFlow: Flow<Boolean>
    val notificationFlow: Flow<Int>
    suspend fun setNotificationInterval(value: Int)
}