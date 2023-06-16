package com.codehunters.glucosereader.ui.notification.service

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import com.codehunters.glucosereader.ui.notification.reminders.ReminderReceiver
import com.codehunters.presenter.interfaces.INotificationPresenter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@AndroidEntryPoint
class ReminderService : Service() {

    @Inject
    lateinit var notificationPresenter: INotificationPresenter

    private val coroutineDispatcher: CoroutineDispatcher = Dispatchers.IO

    private val notificationChannelId = "reminder_channel_id"
    private val notificationChannelName = "Reminder Channel"

    private val scope = CoroutineScope(coroutineDispatcher)

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        notificationPresenter.isNotificationFlow
            .filter { it }
            .distinctUntilChanged()
            .onEach {
                notificationPresenter.notificationFlow.onEach { interval ->
                    scheduleReminder(interval)
                }.launchIn(scope)
            }
            .launchIn(scope)

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun createNotificationChannel() {
        val channel = NotificationChannel(
            notificationChannelId,
            notificationChannelName,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun scheduleReminder(interval: Int) {
        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(this, ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        val currentTimeMillis = System.currentTimeMillis()
        val triggerAtMillis = currentTimeMillis + (interval * 60 * 1000)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            (interval * 60 * 1000).toLong(),
            pendingIntent
        )
    }
}