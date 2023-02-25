package com.codehunters.presenter

import com.codehunters.presenter.interfaces.ITransceiveObject
import com.codehunters.presenter.interfaces.ITransceiveObject.Companion.DEFAULT_BLOCK_SIZE
import com.codehunters.presenter.interfaces.ITransceiveObject.Companion.HEX_ARRAY
import kotlin.experimental.and

class TransceiveObject : ITransceiveObject {
    override fun getData(): List<ByteArray> {
        val data = mutableListOf<ByteArray>()
        for (block in 0..40 step DEFAULT_BLOCK_SIZE) {
            data.add(byteArrayOf(0x02, 0x20, block.toByte()))
        }
        return data
    }

    override fun glucoseReading(value: Int): Float {
        val bitmask = 0x0FFF
        return java.lang.Float.valueOf(java.lang.Float.valueOf(((value and bitmask) / 6).toFloat()) - 37)
    }

    override fun bytesToHex(bytes: ByteArray): String {
        val hexChars = CharArray(bytes.size * 2)
        for (j in bytes.indices) {
            val v: Int = bytes[j].and(0xFF.toByte()).toInt()
            hexChars[j * 2] = HEX_ARRAY[v ushr 4]
            hexChars[j * 2 + 1] = HEX_ARRAY[v and 0x0F]
        }
        return String(hexChars)
    }
}