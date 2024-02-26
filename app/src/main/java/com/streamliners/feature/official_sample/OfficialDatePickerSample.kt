package com.streamliners.feature.official_sample

import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OfficialDatePickerSample(
    showMessageDialog: (String, String) -> Unit
) {

    val openDialog = remember { mutableStateOf(false) }

    Button(
        onClick = { openDialog.value = true }
    ) {
        Text(text = "Show Date Picker")
    }

    if (openDialog.value) {
        val datePickerState = rememberDatePickerState(
            yearRange = 2024..2026,
            selectableDates = object : SelectableDates {
                override fun isSelectableYear(year: Int): Boolean {
                    return year >= 2024
                }
            }
        )

        val confirmEnabled = remember {
            derivedStateOf { datePickerState.selectedDateMillis != null }
        }
        DatePickerDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                        showMessageDialog(
                            "Result",
                            "Selected date timestamp: ${datePickerState.selectedDateMillis}"
                        )
                    },
                    enabled = confirmEnabled.value
                ) {
                    Text("OK")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        openDialog.value = false
                    }
                ) {
                    Text("Cancel")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }
}