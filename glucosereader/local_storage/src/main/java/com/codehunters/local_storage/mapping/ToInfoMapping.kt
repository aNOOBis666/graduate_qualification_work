package com.codehunters.local_storage.mapping

import com.codehunters.local_storage.GlucoseEntity
import com.codehunters.local_storage.models.GlucoseInfo

fun List<GlucoseEntity>.toInfoList() = map { it.toInfo() }

fun GlucoseEntity.toInfo() = GlucoseInfo(
    value = value,
    creationDate = creationDate
)