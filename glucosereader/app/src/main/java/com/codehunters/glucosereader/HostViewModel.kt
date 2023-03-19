package com.codehunters.glucosereader

import android.nfc.Tag
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.codehunters.data.models.GlucoseData
import com.codehunters.presenter.*
import com.codehunters.presenter.interfaces.IGlucosePresenter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class HostViewModel @Inject constructor(
    private val glucosePresenter: IGlucosePresenter
) : ViewModel() {

    fun getNfcData(tag: Tag): Pair<GlucoseReading, GlucoseReading>? {
        val (data, _) = NFCReader.onTag(tag)
        val tagId = RawParser.bin2long(tag.id)
        val now = Time.now()
        data?.let {
            val timeStamp = RawParser.timestamp(it)
            if (timeStamp >= 30) {
                return DataContainer.append(data, now, tagId)
            }
        }
        return null
    }

    private fun saveData(value: Float) {
        viewModelScope.launch(Dispatchers.IO) {
            glucosePresenter.add(
                GlucoseData(value = value, creationDate = System.currentTimeMillis())
            )
        }
    }
}