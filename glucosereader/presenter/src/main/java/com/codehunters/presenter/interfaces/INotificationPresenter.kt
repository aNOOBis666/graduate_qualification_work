package com.codehunters.presenter.interfaces

import kotlinx.coroutines.flow.Flow

interface INotificationPresenter {
    companion object {
        const val EMPTY_NOTIFICATION = 0
    }

    val isNotificationFlow: Flow<Boolean>
    val notificationFlow: Flow<Int>
    suspend fun setNotificationInterval(value: Int)
}