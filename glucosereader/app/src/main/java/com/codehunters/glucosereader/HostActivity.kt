package com.codehunters.glucosereader

import android.nfc.NfcAdapter
import android.nfc.Tag
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.codehunters.glucosereader.databinding.AcHostBinding
import com.codehunters.glucosereader.ui.navigation.NavigationDispatcher
import com.codehunters.glucosereader.utils.parcelableExtra
import com.codehunters.utils.observe
import com.codehunters.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


@AndroidEntryPoint
class HostActivity : AppCompatActivity(R.layout.ac_host) {

    @Inject
    lateinit var navigationDispatcher: NavigationDispatcher

    private val viewModel: HostViewModel by viewModels()
    private val viewBinding by viewBinding(AcHostBinding::bind)

    private val navController: NavController by lazy {
        (supportFragmentManager.findFragmentById(R.id.nav_host_container) as NavHostFragment).navController
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupNavigation()
        intent.parcelableExtra<Tag>(NfcAdapter.EXTRA_TAG)?.let { tag ->
            saveOrError(viewModel.getLibreData(tag).value)
        }
    }

    private fun setupNavigation() {
        navigationDispatcher.navigationCommandFlow.observe(this) { command ->
            command.invoke(navController)
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