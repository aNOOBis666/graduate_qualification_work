package com.codehunters.glucosereader.ui.notification

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codehunters.presenter.interfaces.INotificationPresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationDialogViewModel @Inject constructor(
    private val notificationPresenter: INotificationPresenter
) : ViewModel() {

    fun onSaveInterval(intervalInMinutes: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            notificationPresenter.setNotificationInterval(intervalInMinutes)
        }
    }
}