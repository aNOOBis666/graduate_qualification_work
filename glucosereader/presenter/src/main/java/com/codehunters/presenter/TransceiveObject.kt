package com.codehunters.presenter

import com.codehunters.presenter.interfaces.ITransceiveObject
import com.codehunters.presenter.interfaces.ITransceiveObject.Companion.DEFAULT_BLOCK_SIZE

class TransceiveObject: ITransceiveObject {
    override fun getData(): List<ByteArray> {
        val data = mutableListOf<ByteArray>()
        for (block in 0..40 step DEFAULT_BLOCK_SIZE) {
            data.add(byteArrayOf(0x02, 0x23, block.toByte(), 0x02))
        }
        return data
    }
}