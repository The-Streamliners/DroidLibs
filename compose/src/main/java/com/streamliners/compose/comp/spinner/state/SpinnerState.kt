package com.streamliners.compose.comp.spinner.state

import androidx.compose.runtime.MutableState
import com.streamliners.compose.comp.textInput.state.TextInputState

class SpinnerState<T>(
    val selection: MutableState<T?>,
    val textInputState: MutableState<TextInputState>,
    val labelExtractor: (T) -> String
) {
    companion object

    fun ifSelected(
        lambda: (T) -> Unit
    ) {
        selection.value?.let(lambda)
    }
}