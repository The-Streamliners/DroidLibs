package com.streamliners.feature.pickers_sample.date

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.nullableValue
import com.streamliners.compose.comp.textInput.state.update
import com.streamliners.pickers.date.DatePickerDialog
import com.streamliners.pickers.date.ShowDatePicker
import com.streamliners.pickers.date.ShowDateRangePicker
import com.streamliners.utils.DateTimeUtils
import com.streamliners.utils.DateTimeUtils.Format.DATE_MONTH_YEAR_2
import com.streamliners.utils.dateOnly

@Composable
fun DatePickerSample(
    rangePicker: Boolean,
    showDatePicker: ShowDatePicker,
    showDateRangePicker: ShowDateRangePicker,
    showMessageDialog: (String, String) -> Unit,
    executeHandlingError: (() -> Unit) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Date${if (rangePicker) " Range" else ""} Picker",
                style = MaterialTheme.typography.titleLarge
            )

            val format = remember {
                mutableStateOf<DateTimeUtils.Format?>(DATE_MONTH_YEAR_2)
            }
            val prefill = remember {
                mutableStateOf(TextInputState("Prefill"))
            }
            val minDate = remember {
                mutableStateOf(TextInputState("Min Date"))
            }
            val maxDate = remember {
                mutableStateOf(TextInputState("Max Date"))
            }

            FormatSelection(
                options = DateTimeUtils.Format.dateOnly(),
                state = format,
                onStateChanged = { prevFormat ->
                    listOf(prefill, minDate, maxDate).forEach { state ->
                        state.nullableValue()?.let {
                            state.update(
                                try {
                                    DateTimeUtils.reformatTime(
                                        time = it,
                                        from = prevFormat ?: DATE_MONTH_YEAR_2,
                                        to = format.value ?: DATE_MONTH_YEAR_2
                                    )
                                } catch (e: Exception) {
                                    ""
                                }
                            )
                        }
                    }
                }
            )

            TextInputLayout(state = prefill)

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextInputLayout(modifier = Modifier.weight(1f), state = minDate)
                TextInputLayout(modifier = Modifier.weight(1f), state = maxDate)
            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    executeHandlingError {
                        if (rangePicker) {
                            showDateRangePicker(
                                DatePickerDialog.RangePickerParams(
                                    format = format.value ?: DATE_MONTH_YEAR_2,
                                    prefill = prefill.nullableValue()
                                        ?.split(",")
                                        ?.let { it[0] to it[1] },
                                    onPicked = { range ->
                                        prefill.update("${range.first}, ${range.second}")
                                        showMessageDialog(
                                            "Result",
                                            "You picked ( $range )"
                                        )
                                    }
                                )
                            )
                        } else {
                            showDatePicker(
                                DatePickerDialog.Params(
                                    format = format.value ?: DATE_MONTH_YEAR_2,
                                    prefill = prefill.nullableValue(),
                                    minDate = minDate.nullableValue(),
                                    maxDate = maxDate.nullableValue(),
                                    onPicked = { date ->
                                        prefill.update(date)
                                        showMessageDialog(
                                            "Result",
                                            "You picked ( $date )"
                                        )
                                    }
                                )
                            )
                        }
                    }
                }
            ) {
                Text(text = "Show Date Picker")
            }
        }
    }
}