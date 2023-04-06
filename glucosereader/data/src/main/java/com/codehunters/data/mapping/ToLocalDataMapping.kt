package com.codehunters.data.mapping

import com.codehunters.glucose_reader.models.GlucoseData


fun GlucoseData?.toLocalData() = com.codehunters.data.models.GlucoseData(
    value = this?.currentGlucose ?: 0F,
    creationDate = this?.creationDate ?: 0
)