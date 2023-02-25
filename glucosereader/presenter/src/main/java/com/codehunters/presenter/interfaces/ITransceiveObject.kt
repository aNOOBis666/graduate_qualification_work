package com.codehunters.presenter.interfaces

interface ITransceiveObject {
    companion object {
        const val DEFAULT_BLOCK_SIZE = 3
        val HEX_ARRAY = "0123456789ABCDEF".toCharArray()
    }

    fun getData(): List<ByteArray>
    fun glucoseReading(value: Int): Float
    fun bytesToHex(bytes: ByteArray): String
}