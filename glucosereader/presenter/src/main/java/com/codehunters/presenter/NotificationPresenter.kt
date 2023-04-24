package com.codehunters.presenter

import com.codehunters.presenter.interfaces.INotificationPresenter
import com.codehunters.repository.interfaces.INotificationRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class NotificationPresenter @Inject constructor(
    private val notificationRepository: INotificationRepository
): INotificationPresenter {

    override val isNotificationFlow: Flow<Boolean>
        get() = notificationRepository.isNotificationFlow

    override val notificationFlow: Flow<Int>
        get() = notificationRepository.notificationFlow

    override suspend fun setNotificationInterval(value: Int) {
        notificationRepository.setNotificationInterval(value)
    }
}