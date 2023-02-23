package com.codehunters.repository.mapping

import com.codehunters.data.models.GlucoseData
import com.codehunters.local_storage.models.GlucoseInfo

fun List<GlucoseInfo>.toDataList() = map { it.toData() }

fun GlucoseInfo.toData() = GlucoseData(value = value, creationDate = creationDate)