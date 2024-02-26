package com.streamliners.feature.pickers_sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.base.ext.execute
import com.streamliners.compose.comp.appBar.TitleBarScaffold
import com.streamliners.feature.pickers_sample.date.DatePickerSample
import com.streamliners.pickers.date.ShowDatePicker
import com.streamliners.pickers.date.ShowDateRangePicker

@Composable
fun PickersSampleScreen(
    viewModel: PickersSampleViewModel,
    navController: NavController,
    showDatePicker: ShowDatePicker,
    showDateRangePicker: ShowDateRangePicker,
    showMessageDialog: (String, String) -> Unit
) {
    TitleBarScaffold(
        title = "DroidLibs Pickers Samples",
        navigateUp = { navController.navigateUp() }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            DatePickerSample(
                rangePicker = false,
                showDatePicker = showDatePicker,
                showDateRangePicker = showDateRangePicker,
                showMessageDialog = showMessageDialog,
                executeHandlingError = { viewModel.execute(lambda = { it() })}
            )

            DatePickerSample(
                rangePicker = true,
                showDatePicker = showDatePicker,
                showDateRangePicker = showDateRangePicker,
                showMessageDialog = showMessageDialog,
                executeHandlingError = { viewModel.execute(lambda = { it() })}
            )
        }
    }
}