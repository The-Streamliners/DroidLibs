package com.streamliners.feature.pickers_sample.comp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.streamliners.compose.android.comp.spinner.OutlinedSpinner
import com.streamliners.compose.comp.spinner.state.SpinnerState
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.utils.DateTimeUtils

@Composable
fun DateTimeFormatSelection(
    state: MutableState<DateTimeUtils.Format?>,
    options: List<DateTimeUtils.Format>,
    onStateChanged: (previousValue: DateTimeUtils.Format?) -> Unit
) {
    val spinnerState = remember {
        SpinnerState(
            selection = state,
            textInputState = mutableStateOf(TextInputState("Format")),
            labelExtractor = { it.pattern }
        )
    }

    OutlinedSpinner(
        modifier = Modifier.fillMaxWidth(),
        options = options,
        state = spinnerState,
        onStateChanged = onStateChanged
    )
}