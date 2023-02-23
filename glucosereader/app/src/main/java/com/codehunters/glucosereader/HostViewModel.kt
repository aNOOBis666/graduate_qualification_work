package com.codehunters.glucosereader

import android.nfc.Tag
import android.nfc.tech.NfcA
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codehunters.data.models.GlucoseData
import com.codehunters.presenter.interfaces.IGlucosePresenter
import com.codehunters.presenter.interfaces.ITransceiveObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HostViewModel @Inject constructor(
    private val transceiveObject: ITransceiveObject,
    private val glucosePresenter: IGlucosePresenter
) : ViewModel() {

    fun getNfcData(tag: Tag?): List<ByteArray> {
        var data: List<ByteArray>
        NfcA.get(tag).apply {
            connect()
            data = sendData()
            close()
        }
        return data
    }

    private fun NfcA.sendData(): List<ByteArray> {
        val receivedInfo = mutableListOf<ByteArray>()
        transceiveObject.getData().map {
            runCatching {
                transceive(it)
            }.getOrNull()?.let {
                receivedInfo.add(it)
            }
        }
        return receivedInfo
    }

    private fun saveData(value: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            glucosePresenter.add(
                GlucoseData(value = value, creationDate = System.currentTimeMillis())
            )
        }
    }
}