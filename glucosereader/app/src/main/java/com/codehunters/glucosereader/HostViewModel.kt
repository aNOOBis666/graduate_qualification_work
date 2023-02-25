package com.codehunters.glucosereader

import android.nfc.Tag
import android.nfc.tech.NfcV
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codehunters.data.models.GlucoseData
import com.codehunters.presenter.interfaces.IGlucosePresenter
import com.codehunters.presenter.interfaces.ITransceiveObject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class HostViewModel @Inject constructor(
    private val transceiveObject: ITransceiveObject,
    private val glucosePresenter: IGlucosePresenter
) : ViewModel() {

    private var currentGlucose = 0f

    fun getNfcData(tag: Tag?): String? {
        var data: List<ByteArray>
        var glucose: String?
        NfcV.get(tag).apply {
            connect()
            glucose = doInBackground()
            data = sendData()
            close()
        }
        return glucose
    }

    private fun NfcV.sendData(): List<ByteArray> {
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

    fun NfcV.doInBackground(): String? {
        var lectura = ""

        transceiveObject.getData().map {
            var oneBlock = transceive(it)
            oneBlock = Arrays.copyOfRange(oneBlock, 1, oneBlock.size)
            lectura = lectura + transceiveObject.bytesToHex(oneBlock) + "\r\n"
        }
        val s = ""
        val current = s.substring(4, 6).toInt(16)
        val bloque1 = arrayOfNulls<String>(16)
        var ii = 0
        run {
            var i = 8
            while (i < 8 + 15 * 12) {
                bloque1[ii] = s.substring(i, i + 12)
                val g = s.substring(i + 2, i + 4) + s.substring(i, i + 2)
                if (current == ii) {
                    currentGlucose = transceiveObject.glucoseReading(g.toInt(16))
                }
                ii++
                i += 12
            }
        }
        lectura = lectura + "Current approximate glucose " + currentGlucose
        return lectura
    }

}