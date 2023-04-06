package com.codehunters.glucose_reader.libre

//class SensorData() {
//
//    companion object {
//        val DEFAULT = Pair(-0.04605, 0.00567)
//        private const val DEFAULT_MULTIPLIER = 1.0F
//    }
//
//    private val map = HashMap<Long, Pair<Double,Double>>()
//
//    fun get(sensorId : Long) : Pair<Double, Double> {
//        if(sensorId in map) return map[sensorId]!!
//        return DEFAULT
//    }
//
//    private fun getMultiplier() : Float = DEFAULT_MULTIPLIER
//
//    fun sensorToUnit(value : Int, sensorId: Long) : Double = sensorToUnit(value, sensorId, getMultiplier())
//    private fun sensorToUnit(value : Int, sensorId: Long, multiplier : Float) : Double = multiplier * sensorToMmol(value, sensorId)
//    private fun sensorToMmol(value: Int, sensorId: Long): Double = sensorToMmol(value, get(sensorId))
//    private fun sensorToMmol(value: Int, p : Pair<Double, Double>) : Double = value*p.second + p.first
//}