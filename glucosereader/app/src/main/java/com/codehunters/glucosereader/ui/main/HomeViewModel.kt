package com.codehunters.glucosereader.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codehunters.data.models.GlucoseData
import com.codehunters.presenter.interfaces.IGlucosePresenter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val glucosePresenter: IGlucosePresenter
) : ViewModel() {

    private val _uiState = MutableStateFlow<List<GlucoseData>>(emptyList())
    val uiState = _uiState.asStateFlow()

    init {
        renderGlucose()
    }

    private fun renderGlucose() {
        viewModelScope.launch(Dispatchers.IO) {
            glucosePresenter.getAllFlow().collect {
                _uiState.value = it
            }
        }
    }
}