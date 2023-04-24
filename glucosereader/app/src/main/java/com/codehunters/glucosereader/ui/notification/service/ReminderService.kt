package com.codehunters.glucosereader.ui.notification.service

import android.app.AlarmManager
import android.content.Context
import com.codehunters.presenter.interfaces.INotificationPresenter
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderService(
    private val context: Context,
    private val notificationPresenter: INotificationPresenter,
    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO
): IReminderService {

    private val scope = CoroutineScope(coroutineDispatcher)

    private var alarmManager: AlarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun restoreReminders() {
        scope.launch {
            restoreRemindersToAlarmManager()
        }
    }

    private suspend fun restoreRemindersToAlarmManager() {
        deleteRemindersFromAlarmManager()
        resubscribe
    }

    private suspend fun deleteRemindersFromAlarmManager() {
        val reminders = notificationsRepository.getAllReminders()
        for (reminder in reminders) {
            deleteReminderFromAlarmManager(reminder)
        }
    }
}