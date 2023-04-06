package com.codehunters.glucose_reader.libre

data class SensorChunk(val value: Int, val status: Int, val history: Boolean, val rest: Int) {
    constructor(b : ByteArray, history : Boolean) :
            this(RawParser.bin2int(b[1], b[0]),
                RawParser.byte2uns(b[2]),
                history,
                RawParser.bin2int(b[3], b[4], b[5]))
}
data class GlucoseReading(val value: Int, val utcTimeStamp: Long, val sensorId: Long, val status: Int, val history: Boolean, val rest: Int) {
    constructor(s: SensorChunk, utcTimeStamp: Long, sensorId: Long):
            this(
                s.value,
                utcTimeStamp,
                sensorId,
                s.status,
                s.history,
                s.rest
            )
}