package com.codehunters.glucosereader.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.documentfile.provider.DocumentFile
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.codehunters.data.models.GlucoseData
import com.codehunters.glucosereader.R
import com.codehunters.glucosereader.databinding.FragmentHomeBinding
import com.codehunters.utils.observe
import com.codehunters.utils.showSnackbar
import dagger.hilt.android.AndroidEntryPoint
import org.apache.poi.hssf.usermodel.HSSFWorkbook
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val viewBinding by viewBinding(FragmentHomeBinding::bind)
    private val viewModel: HomeViewModel by viewModels()

    companion object {
        private const val DEFAULT_FILE_NAME = "Glucose_history.xls"
    }

    private var glucoseHistory: List<GlucoseData> = emptyList()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding.notification.setOnClickListener { viewModel.addOrDeleteNotification() }
        viewModel.uiState.observe(viewLifecycleOwner, observer = ::renderGraph)
        viewModel.isNotificationState.observe(viewLifecycleOwner, observer = ::renderIsNotification)
    }

    private fun renderGraph(data: List<GlucoseData>) {
        viewBinding.currentGlucose.text =
            data.lastOrNull()?.value?.toString() ?: getString(R.string.error_empty_glucose)
        viewBinding.graphic.setItemsList(data)
        viewBinding.addButton.setOnClickListener {
            glucoseHistory = data
            openFilePicker()
        }
    }

    private fun renderIsNotification(isNotification: Boolean) {
        viewBinding.notification.isSelected = isNotification
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT_TREE)
        saveFileLauncher.launch(intent)
    }

    private fun exportMapToXLS(filePath: String) {
        val workbook = HSSFWorkbook()
        val sheet = workbook.createSheet(getString(R.string.glucose_values))

        var rowNumber = 0
        glucoseHistory.map {
            val row = sheet.createRow(rowNumber++)
            val keyCell = row.createCell(0)
            keyCell.setCellValue(convertUnixToDate(it.creationDate))
            val valueCell = row.createCell(1)
            valueCell.setCellValue(it.value.toString())
        }

        val resolver = requireContext().contentResolver
        val outputStream = resolver.openOutputStream(Uri.parse(filePath))

        val infoMessageId = if (outputStream != null) {
            workbook.write(outputStream)
            outputStream.close()
            workbook.close()
            R.string.file_correct_operation
        } else {
            R.string.file_incorrect_operation
        }
        viewBinding.root.showSnackbar(getString(infoMessageId))
    }

    private val saveFileLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val selectedDirUri = result.data?.data
                if (selectedDirUri != null) {
                    val selectedDir = DocumentFile.fromTreeUri(requireContext(), selectedDirUri)
                    val file =
                        selectedDir?.createFile("application/vnd.ms-excel", DEFAULT_FILE_NAME)

                    if (file != null) {
                        val filePath = file.uri.toString()
                        exportMapToXLS(filePath)
                    }
                }
            } else {
                viewBinding.root.showSnackbar(getString(R.string.path_choosing_cancelled))
            }
        }

    private fun convertUnixToDate(unixTime: Long): String {
        val date = Date(unixTime)
        val format = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        return format.format(date)
    }
}