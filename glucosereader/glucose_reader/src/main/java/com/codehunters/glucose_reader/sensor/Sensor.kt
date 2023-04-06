package com.codehunters.glucose_reader.sensor

import com.codehunters.glucose_reader.models.GlucoseData

interface Sensor {
    fun glucoseData(tag: Any? = null): GlucoseData?
}