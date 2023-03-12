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
        viewModel.uiState.observe(viewLifecycleOwner, observer = ::renderGraph)
    }

    private fun renderGraph(data: List<GlucoseData>) {
        viewBinding.currentGlucose.text = data.lastOrNull()?.value?.toString().orEmpty()
        viewBinding.graphic.setItemsList(data)
    }
}