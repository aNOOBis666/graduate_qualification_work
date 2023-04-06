package com.codehunters.glucose_reader.sensor.typed_sensors

import android.nfc.Tag
import com.codehunters.glucose_reader.libre.DataContainer
import com.codehunters.glucose_reader.libre.NFCReader
import com.codehunters.glucose_reader.libre.RawParser
import com.codehunters.glucose_reader.mapping.toGlucoseData
import com.codehunters.glucose_reader.models.GlucoseData
import com.codehunters.glucose_reader.sensor.Sensor

class LibreSensor: Sensor {
    override fun glucoseData(tag: Any?): GlucoseData? {
        (tag as? Tag)?.let {
            val data = NFCReader.onTag(tag)
            val tagId = RawParser.bin2long(tag.id)
            val now = System.currentTimeMillis()
            data?.let {
                val timeStamp = RawParser.timestamp(it)
                if (timeStamp >= 30) {
                    return DataContainer.append(data, now, tagId)?.second.toGlucoseData()
                }
            }
        }
        return null
    }
}