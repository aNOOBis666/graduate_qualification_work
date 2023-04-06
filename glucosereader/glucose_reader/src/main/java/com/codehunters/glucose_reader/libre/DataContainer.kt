package com.codehunters.glucose_reader.libre

import com.codehunters.glucose_reader.utils.TimeUtils
import kotlin.math.min

object DataContainer {
    private val DEFAULT = Pair(-0.04605, 0.00567)
    private const val noH = 32
    private val lock = Object()
    private var lastTimeStamp : Int = 0
    private var readings : MutableList<ByteArray> = mutableListOf()


    fun append(raw_data: ByteArray, readingTime: Long, sensorId : Long): Pair<GlucoseReading, GlucoseReading>? {
        synchronized(lock) {
            readings.add(raw_data.copyOf())
            val timestamp = RawParser.timestamp(raw_data)
            // Timestamp is 2 mod 15 every time a new reading to history is done.
            val minutesSinceLast = (timestamp + 12) % 15
            val start = readingTime - TimeUtils.MINUTE * (15 * (noH - 1) + minutesSinceLast)
            val nowHistory = RawParser.history(raw_data)
            val nowRecent = RawParser.recent(raw_data)

            val historyPrepared = prepare(nowHistory, sensorId, 15 * TimeUtils.MINUTE, start, minutesSinceLast != 14 && timestamp < TimeUtils.DURATION_MINUTES)
            val startRecent =
                if (historyPrepared.isEmpty()) {
                    min(System.currentTimeMillis(), readingTime - 16 * TimeUtils.MINUTE)
                } else {
                    min(readingTime - 16 * TimeUtils.MINUTE, historyPrepared.last().utcTimeStamp)
                }

            val recentPrepared = prepare(nowRecent, sensorId, 1 * TimeUtils.MINUTE, startRecent, true)
            val added = extend(recentPrepared) + extend(historyPrepared)
            lastTimeStamp = timestamp
            return guess(added)
        }
    }

    private fun extend(v: List<GlucoseReading>) : List<GlucoseReading> {
        return v.filter { g: GlucoseReading -> g.status != 0 && g.value > 10 }
    }

    // Inspects last entry from the same sensor and filters out all that are already logged.
    private fun prepare(chunks : List<SensorChunk>,
                        sensorId : Long,
                        dt : Long,
                        start : Long,
                        certain : Boolean
    ) : List<GlucoseReading> {
        val range = IntRange(0, chunks.size - 1)
        return if(!certain)
            chunks.slice(range)
                .mapIndexed { i: Int, chunk: SensorChunk ->
                    GlucoseReading(chunk,
                        System.currentTimeMillis() + (i + 1) * dt, sensorId)
                }
        else {
            chunks.mapIndexed { i: Int, chunk: SensorChunk ->
                GlucoseReading(chunk,
                    start + i * dt, sensorId)
            }.slice(range)
        }
    }

    private fun nice(g : GlucoseReading) : Boolean = g.status == 200 && (g.value in 11..4999)

    private fun guess(candidates: List<GlucoseReading>) : Pair<GlucoseReading, GlucoseReading>? {
        synchronized(lock) {
            if (candidates.isNotEmpty()) {
                val last = candidates.last()
                if (!nice(last)) return null
                val lastAsReading = GlucoseReading(
                    last.value,
                    last.utcTimeStamp,
                    last.sensorId,
                    last.status,
                    false,
                    0
                )
                val real =
                    candidates.filter { g -> !g.history && g.sensorId == last.sensorId }
                if (real.isEmpty()) return null
                val entry = real.first()
                val guess = last.value * 2 - entry.value
                val time = last.utcTimeStamp * 2 - entry.utcTimeStamp
                return Pair(
                    lastAsReading, GlucoseReading(
                        (guess * DEFAULT.second - DEFAULT.first).toInt(),
                        time,
                        last.sensorId, last.status, false, 0
                    )
                )
            } else return null
        }
    }
}