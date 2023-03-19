package com.codehunters.presenter

import android.annotation.SuppressLint
import android.content.Context

class SensorData(context: Context) {
    val map = HashMap<Long, Pair<Double,Double>>()
    private val ctx : Context = context

    fun get(sensorId : Long) : Pair<Double, Double> {
        if(sensorId in map) return map[sensorId]!!
        return default
    }

    fun get_multiplier() : Float = UserData.get_multiplier(ctx)


    fun sensor2unit(value : Int, sensorId: Long, multiplier : Float) : Double = multiplier * sensor2mmol(value, sensorId)
    fun sensor2unit(value : Int, sensorId: Long) : Double = sensor2unit(value, sensorId, get_multiplier())
    private fun sensor2mmol(value: Int, sensorId: Long): Double = sensor2mmol(value, get(sensorId))
    private fun sensor2mmol(value: Int, p : Pair<Double, Double>) : Double = value*p.second + p.first

    companion object {
        @SuppressLint("StaticFieldLeak")
        var sData : SensorData? = null
        val default = Pair(-0.04605, 0.00567)
    }
}