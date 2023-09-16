package com.streamliners.compose.comp.spinner.state

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.update
import com.streamliners.compose.comp.textInput.state.validate

class SpinnerState<T>(
    val state: MutableState<T?>,
    val textInputState: MutableState<TextInputState>,
    val labelExtractor: (T) -> String
) {
    companion object
}