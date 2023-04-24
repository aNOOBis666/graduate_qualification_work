package com.codehunters.glucosereader.ui.notification

import android.os.Bundle
import android.view.View
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.codehunters.glucosereader.R
import com.codehunters.glucosereader.databinding.DlgNotificationBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NotificationDialogFragment : DialogFragment(R.layout.dlg_notification) {

    private val viewBinding by viewBinding(DlgNotificationBinding::bind)
    private val viewModel: NotificationDialogViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.progress.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) =
                with(viewBinding) {
                    currentInterval.text = progress.toString()
                    submit.isEnabled = progress > 0
                    submit.setOnClickListener {
                        viewModel.onSaveInterval(progress)
                        dialog?.cancel()
                    }
                }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        viewBinding.cancel.setOnClickListener { dialog?.cancel() }
    }
}