package com.codehunters.repository.mapping

import com.codehunters.data.models.GlucoseData
import com.codehunters.local_storage.models.GlucoseInfo

fun List<GlucoseData>.toInfoList() = map { it.toInfo() }

fun GlucoseData.toInfo() = GlucoseInfo(value = value, creationDate = creationDate)