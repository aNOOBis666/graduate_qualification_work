package com.codehunters.glucosereader.ui.notification.reminders

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BootUpReceiver : BroadcastReceiver() {

//    @Inject
//    private val reminderService: IReminderService

    override fun onReceive(p0: Context?, p1: Intent?) {
//        reminderService.restoreReminders()
    }
}