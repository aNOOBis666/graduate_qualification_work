package com.codehunters.glucose_reader.mapping

import com.codehunters.glucose_reader.libre.GlucoseReading
import com.codehunters.glucose_reader.models.GlucoseData

fun GlucoseReading?.toGlucoseData() = GlucoseData.Libre(
    id = this?.sensorId?.toInt() ?: 0,
    currentGlucose = this?.value?.toFloat() ?: 0F,
    creationDate = this?.utcTimeStamp ?: 0
)