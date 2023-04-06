package com.codehunters.glucosereader

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.codehunters.glucosereader.databinding.AcHostBinding
import com.codehunters.glucosereader.utils.parcelableExtra
import com.codehunters.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class HostActivity : AppCompatActivity(R.layout.ac_host) {

    private val viewModel: HostViewModel by viewModels()
    private val viewBinding by viewBinding(AcHostBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        intent.parcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)?.let { tag ->
            saveOrError(viewModel.getLibreData(tag).value)
        }
    }

    private fun saveOrError(currentValue: Float) {
        if (currentValue != 0.0F) viewModel.saveData(currentValue)
        else renderError()
    }

    private fun renderError() {
        viewBinding.root.showSnackbar(getString(R.string.error_reading_glucose))
    }
}