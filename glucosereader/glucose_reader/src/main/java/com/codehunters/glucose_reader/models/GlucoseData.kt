package com.codehunters.glucose_reader.models

sealed class GlucoseData(
    open val id: Int,
    open val currentGlucose: Float,
    open val creationDate: Long,
    open val prediction: Float? = null,
    open val expireDate: Long? = null,
    open val temperature: Float? = null
) {

    data class Libre(
        override val id: Int,
        override val currentGlucose: Float,
        override val creationDate: Long
    ) : GlucoseData(id, currentGlucose, creationDate)
}
