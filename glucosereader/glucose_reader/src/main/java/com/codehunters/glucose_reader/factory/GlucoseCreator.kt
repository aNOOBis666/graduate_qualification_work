package com.codehunters.glucose_reader.factory

import com.codehunters.glucose_reader.models.GlucoseData
import com.codehunters.glucose_reader.sensor.Sensor

interface GlucoseCreator {
    fun readValues(tag: Any? = null): GlucoseData? {
        return connectSensor().glucoseData(tag)
    }


    fun connectSensor(): Sensor
}