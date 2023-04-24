package com.codehunters.repository

import com.codehunters.local_storage.interfaces.ISharedPreferencesManager
import com.codehunters.repository.interfaces.INotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationRepository @Inject constructor(
    private val sharedPreferencesManager: ISharedPreferencesManager
): INotificationRepository {

    override val isNotificationFlow: Flow<Boolean>
        get() = sharedPreferencesManager.isNotificationFlow

    override val notificationFlow: Flow<Int>
        get() = sharedPreferencesManager.notificationFlow

    override suspend fun setNotificationInterval(value: Int) {
        sharedPreferencesManager.setNotificationInterval(value)
    }
}