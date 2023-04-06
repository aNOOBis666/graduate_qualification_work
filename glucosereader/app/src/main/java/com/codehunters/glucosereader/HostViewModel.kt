package com.codehunters.glucosereader

import android.nfc.Tag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codehunters.data.mapping.toLocalData
import com.codehunters.data.models.GlucoseData
import com.codehunters.glucose_reader.IGlucoseReader
import com.codehunters.glucose_reader.types.SensorTypes
import com.codehunters.presenter.interfaces.IGlucosePresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HostViewModel @Inject constructor(
    private val glucosePresenter: IGlucosePresenter,
    private val glucoseReader: IGlucoseReader
) : ViewModel() {

//    Перенести логику в либреридер
//    Добавить сохранение типов в бд с отдельным интерфейсом

    fun getLibreData(tag: Tag): GlucoseData {
        return glucoseReader.getValues(SensorTypes.LIBRE_TYPE, tag).toLocalData()
    }

    fun saveData(value: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            glucosePresenter.add(
                GlucoseData(value = value, creationDate = System.currentTimeMillis())
            )
        }
    }
}