package com.codehunters.glucose_reader.libre

import android.nfc.Tag
import android.nfc.tech.NfcV
import android.util.Log
import com.codehunters.glucose_reader.utils.TimeUtils

class NFCReader {
    companion object {
        private const val LOG_TAG = "READING_NFC"

        private fun startCmd(): ByteArray = byteArrayOf(0x02, 0xA0 - 0x100, 0x07, 0xC2 - 0x100, 0xAD - 0x100, 0x75, 0x21)
        private fun statusCmd(): ByteArray = byteArrayOf(0x02, 0xA1 - 0x100, 0x07)

        fun onTag(tag: Tag): ByteArray? {
            val nfcvTag = NfcV.get(tag)
            val uid: ByteArray = tag.id
            val resp: ByteArray
            try {
                nfcvTag.connect()
                resp = nfcvTag.transceive(statusCmd())
            } catch (e: Exception) {
                return null
            }
            val zero: Byte = 0x00
            return if (resp[5] == zero && resp[6] == zero) {
                start(nfcvTag, startCmd())
            } else {
                readout(nfcvTag, uid)
            }
        }

        private fun start(nfcvTag: NfcV, startCmd: ByteArray): ByteArray? {
            return nfcvTag.transceive(startCmd)
        }

        private fun readout(nfcvTag: NfcV, uid: ByteArray): ByteArray? {
            val data = ByteArray(360)
            // Get bytes [i*8:(i+1)*8] from sensor memory and stores in data
            for (i in 0..40) {
                val cmd = byteArrayOf(0x60, 0x20, 0, 0, 0, 0, 0, 0, 0, 0, i.toByte(), 0)
                System.arraycopy(uid, 0, cmd, 2, 8)
                var resp: ByteArray
                val time = System.currentTimeMillis()
                while (true) {
                    try {
                        resp = nfcvTag.transceive(cmd)
                        resp = resp.copyOfRange(2, resp.size)
                        System.arraycopy(resp, 0, data, i * 8, resp.size)
                        break
                    } catch (e: Exception) {
                        if (System.currentTimeMillis() > time + TimeUtils.SECOND * 5) {
                            Log.e(LOG_TAG, "Timeout: took more than 5 seconds to read nfctag")
                            return null
                        }
                    }
                }
            }

            try {
                nfcvTag.close()
            } catch (e: Exception) {
                Log.e(LOG_TAG, "Error closing tag!")
            }
            return data
        }
    }
}