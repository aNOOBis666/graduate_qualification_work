package com.codehunters.glucosereader

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.codehunters.glucosereader.utils.parcelableExtra
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HostActivity : AppCompatActivity(R.layout.ac_host) {

    private val viewModel: HostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.parcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)?.let { tag ->
            val received = viewModel.getNfcData(tag)
            Log.e("NfcReaded", received.toString())
            Log.e("NfcReaded", tag.id.toString())
            Log.e("NfcReaded", tag.techList.toString())
        }
    }
}