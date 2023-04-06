package com.codehunters.glucose_reader.utils

class TimeUtils {
    companion object {
        const val SECOND = 1000L
        const val MINUTE = 60L * SECOND
        const val HOUR = 60L * MINUTE
        const val DAY = 24L * HOUR
        const val DURATION_MINUTES = 20160
        fun now(): Long = System.currentTimeMillis()
    }
}