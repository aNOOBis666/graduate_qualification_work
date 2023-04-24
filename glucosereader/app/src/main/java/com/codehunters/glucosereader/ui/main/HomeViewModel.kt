package com.codehunters.glucosereader.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codehunters.data.models.GlucoseData
import com.codehunters.glucosereader.ui.navigation.INavigation
import com.codehunters.presenter.interfaces.IGlucosePresenter
import com.codehunters.presenter.interfaces.INotificationPresenter
import com.codehunters.presenter.interfaces.INotificationPresenter.Companion.EMPTY_NOTIFICATION
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val glucosePresenter: IGlucosePresenter,
    private val notificationPresenter: INotificationPresenter,
    private val navigation: INavigation
) : ViewModel() {

    companion object {
        private const val DEFAULT_VALUES_ON_SCREEN = 10
    }

    private val _uiState = MutableStateFlow<List<GlucoseData>>(emptyList())
    val uiState = _uiState.asStateFlow()

    private val _isNotificationState = MutableStateFlow(false)
    val isNotificationState = _isNotificationState.asStateFlow()

    init {
        renderGlucose()
        isSelectedNotification()
    }

    private fun renderGlucose() {
        viewModelScope.launch(Dispatchers.IO) {
            glucosePresenter
                .getAllFlow()
                .collect {
                    _uiState.value = it.takeLast(DEFAULT_VALUES_ON_SCREEN)
                }
        }
    }

    fun addValue() {
        viewModelScope.launch(Dispatchers.IO) {
            glucosePresenter.add(
                GlucoseData(
                    (1..21).random().toFloat(),
                    System.currentTimeMillis()
                )
            )
        }
    }

    private fun isSelectedNotification() {
        viewModelScope.launch(Dispatchers.IO) {
            notificationPresenter.isNotificationFlow.collect {
                _isNotificationState.value = it
            }
        }
    }

    fun addOrDeleteNotification() {
        viewModelScope.launch(Dispatchers.IO) {
            if (isNotificationState.value) notificationPresenter.setNotificationInterval(
                EMPTY_NOTIFICATION
            )
            else showNotificationDialog()
        }
    }

    private fun showNotificationDialog() {
        viewModelScope.launch(Dispatchers.IO) {
            navigation.showNotificationDialog()
        }
    }
}