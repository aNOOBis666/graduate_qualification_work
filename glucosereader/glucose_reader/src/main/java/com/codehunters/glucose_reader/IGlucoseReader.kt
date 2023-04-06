package com.codehunters.glucose_reader

import com.codehunters.glucose_reader.models.GlucoseData
import com.codehunters.glucose_reader.types.SensorTypes

interface IGlucoseReader {
    fun getValues(sensorType: SensorTypes, tag: Any? = null): GlucoseData?
}