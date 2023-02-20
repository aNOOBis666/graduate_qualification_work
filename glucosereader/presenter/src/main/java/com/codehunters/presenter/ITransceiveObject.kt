package com.codehunters.presenter

interface ITransceiveObject {
    companion object {
        const val DEFAULT_BLOCK_SIZE = 3
    }

    fun getData(): List<ByteArray>
}