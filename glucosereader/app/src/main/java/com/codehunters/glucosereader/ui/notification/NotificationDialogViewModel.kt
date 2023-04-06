package com.codehunters.glucosereader.ui.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationDialogViewModel: ViewModel() {

    fun onSaveInterval(intervalInMinutes: Int) {
        viewModelScope.launch(Dispatchers.IO) {

        }
    }
}