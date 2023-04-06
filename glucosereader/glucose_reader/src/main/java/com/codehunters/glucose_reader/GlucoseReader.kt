package com.codehunters.glucose_reader

import com.codehunters.glucose_reader.factory.factory_method.LibreReader
import com.codehunters.glucose_reader.models.GlucoseData
import com.codehunters.glucose_reader.types.SensorTypes

class GlucoseReader: IGlucoseReader {

    override fun getValues(sensorType: SensorTypes, tag: Any?): GlucoseData? {
        return when (sensorType) {
            SensorTypes.LIBRE_TYPE -> LibreReader()
        }.readValues(tag)
    }
}