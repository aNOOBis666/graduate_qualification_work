package com.codehunters.glucosereader.ui.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.codehunters.data.models.GlucoseData
import com.codehunters.glucosereader.R
import com.codehunters.glucosereader.databinding.FragmentHomeBinding
import com.codehunters.utils.observe
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.addButton.setOnClickListener { viewModel.addValue() }
        viewBinding.notification.setOnClickListener { viewModel.addOrDeleteNotification() }
        viewModel.uiState.observe(viewLifecycleOwner, observer = ::renderGraph)
        viewModel.isNotificationState.observe(viewLifecycleOwner, observer = ::renderIsNotification)
    }

    private fun renderGraph(data: List<GlucoseData>) {
        viewBinding.currentGlucose.text = data.lastOrNull()?.value?.toString() ?: getString(R.string.error_empty_glucose)
        viewBinding.graphic.setItemsList(data)
    }

    private fun renderIsNotification(isNotification: Boolean) {
        viewBinding.notification.isSelected = isNotification
    }

//    private fun getRecentText(guess : Pair<GlucoseReading, GlucoseReading>?, sd: SensorData, ) : String{
//        if(guess != null) {
//            val diff = (guess.second.toUnit(sd) - guess.first.toUnit(sd))/sd.getMultiplier()
//            val trend = if (diff > .5) "↑↑" else if (diff > .25) "↑" else if (diff < -.5) "↓↓" else if (diff < -.25) "↓" else "→"
//            if (TimeUtils.now() - guess.second.utcTimeStamp < TimeUtils.HOUR)
//                return String.format("%.1f %s", guess.second.toUnit(sd), trend)
//        }
//        return "-"
//    }
}