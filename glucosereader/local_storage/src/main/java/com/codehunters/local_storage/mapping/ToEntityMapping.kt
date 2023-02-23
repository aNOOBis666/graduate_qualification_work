package com.codehunters.local_storage.mapping

import com.codehunters.local_storage.GlucoseEntity
import com.codehunters.local_storage.models.GlucoseInfo

fun List<GlucoseInfo>.toEntityList() = map { it.toEntity() }

fun GlucoseInfo.toEntity() = GlucoseEntity(
    value = value,
    creationDate = creationDate
)