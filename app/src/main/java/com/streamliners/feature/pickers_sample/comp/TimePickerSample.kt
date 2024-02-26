package com.streamliners.feature.pickers_sample.comp

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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.nullableValue
import com.streamliners.compose.comp.textInput.state.update
import com.streamliners.pickers.time.ShowTimePicker
import com.streamliners.pickers.time.TimePickerDialog
import com.streamliners.utils.DateTimeUtils
import com.streamliners.utils.DateTimeUtils.Format.HOUR_MIN_24
import com.streamliners.utils.timeOnly

@Composable
fun TimePickerSample(
    showTimePicker: ShowTimePicker,
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
                text = "Time Picker",
                style = MaterialTheme.typography.titleLarge
            )

            val format = remember {
                mutableStateOf<DateTimeUtils.Format?>(HOUR_MIN_24)
            }
            val prefill = remember {
                mutableStateOf(TextInputState("Prefill"))
            }
            val minTime = remember {
                mutableStateOf(TextInputState("Min Time"))
            }
            val maxTime = remember {
                mutableStateOf(TextInputState("Max Time"))
            }

            DateTimeFormatSelection(
                options = DateTimeUtils.Format.timeOnly(),
                state = format,
                onStateChanged = { prevFormat ->
                    reformatInputTimes(prevFormat, format, prefill, minTime, maxTime)
                }
            )

            TextInputLayout(state = prefill)

            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                TextInputLayout(modifier = Modifier.weight(1f), state = minTime)
                TextInputLayout(modifier = Modifier.weight(1f), state = maxTime)
            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    executeHandlingError {
                        showTimePicker(
                            TimePickerDialog.Params(
                                title = "Pick Time",
                                format = format.value ?: HOUR_MIN_24,
                                prefill = prefill.nullableValue(),
                                minTime = minTime.nullableValue(),
                                maxTime = maxTime.nullableValue(),
                                onPicked = { time ->
                                    prefill.update(time)
                                    showMessageDialog(
                                        "Result",
                                        "You picked ( $time )"
                                    )
                                }
                            )
                        )
                    }
                }
            ) {
                Text(text = "Show Time Picker")
            }
        }
    }
}

private fun reformatInputTimes(
    prevFormat: DateTimeUtils.Format?,
    format: MutableState<DateTimeUtils.Format?>,
    prefill: MutableState<TextInputState>,
    minDate: MutableState<TextInputState>,
    maxDate: MutableState<TextInputState>
) {
    listOf(prefill, minDate, maxDate).forEach { state ->
        state.nullableValue()?.let { date ->
            state.update(
                try {
                    DateTimeUtils.reformatTime(
                        time = date,
                        from = prevFormat ?: HOUR_MIN_24,
                        to = format.value ?: HOUR_MIN_24
                    )
                } catch (e: Exception) {
                    ""
                }
            )
        }
    }
}