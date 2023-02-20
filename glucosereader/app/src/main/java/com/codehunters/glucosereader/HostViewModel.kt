package com.codehunters.glucosereader

import android.nfc.Tag
import android.nfc.tech.NfcA
import androidx.lifecycle.ViewModel
import com.codehunters.presenter.ITransceiveObject
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HostViewModel @Inject constructor(
    private val transceiveObject: ITransceiveObject
) : ViewModel() {

    fun getNfcData(tag: Tag?) {
        NfcA.get(tag).apply {
            this.connect()
            this.sendData()
            this.close()
        }
    }

    private fun NfcA.sendData(): List<ByteArray?> {
        return transceiveObject.getData().map {
            var isNotReceived = true
            var receivedInfo = byteArrayOf()
            while (isNotReceived) {
                runCatching {
                    transceive(it)
                }.getOrNull()?.let {
                    isNotReceived = false
                    receivedInfo = it
                }
            }
            receivedInfo
        }
    }
}