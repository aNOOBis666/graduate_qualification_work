package com.codehunters.presenter

import android.util.Log

class RawParser {
    companion object {
        fun bin2int(a: Byte, b: Byte) : Int = (byte2uns(a) shl 8) or byte2uns(b)
        fun bin2int(a: Byte, b: Byte, c: Byte) : Int = (byte2uns(a) shl 16) or (byte2uns(b) shl 8) or byte2uns(c)
        fun byte2uns(a: Byte) : Int = (a.toInt() + 256)%256
        fun bin2long(b: ByteArray) : Long {
            var r = 0L
            for (i in 0..7) {
                r = r shl 8
                r = r or byte2uns(b[i]).toLong()
            }
            return r
        }


        private fun chunk(bytes: ByteArray, history: Boolean) : List<SensorChunk> {
            return bytes
                .toList()
                .windowed(6, 6,false) { list -> SensorChunk(list.toByteArray(), history) }
        }

        // each sample contains 6 bytes. History consists of 32 samples in a cyclical buffer
        // spanning the last 8 hours (4 samples per hour). index 27 of data tells us where in the
        // buffer, the next value will be written. The buffer starts at index 124.
        fun history(data: ByteArray): List<SensorChunk> {
            return try {
                val (iH, startH) = Pair(data[27], 124)
                val flatHistory = data.sliceArray(IntRange(startH + iH * 6, startH + 32 * 6 - 1))
                    .plus(data.sliceArray(IntRange(startH, startH + iH * 6 - 1)))
                chunk(flatHistory, true)
            }catch(e:Exception) {
                Log.e("RAW_PARSER", e.toString())
                listOf()
            }
        }

        // Similar to history, but only stores 16 values, from the last 16 minutes.
        fun recent(data: ByteArray): List<SensorChunk> {
            return try {
                val (iR, startR) = Pair(data[26], 28)
                val flatRecent = data.sliceArray(IntRange(startR + iR*6, startR + 16*6 - 1))
                    .plus(data.sliceArray(IntRange(startR, startR + iR*6 - 1)))
                chunk(flatRecent, false)
            }catch(e:Exception) {
                Log.e("RAWPARSER", e.toString())
                listOf()
            }
        }

        fun timestamp(data: ByteArray) : Int {
            return bin2int(data[317], data[316])
        }
    }
}