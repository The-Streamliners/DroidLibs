package com.streamliners.compose.comp.spinner.state

import androidx.compose.runtime.MutableState
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.update

class SpinnerState<T>(
    val selection: MutableState<T?>,
    val textInputState: MutableState<TextInputState>,
    val labelExtractor: (T) -> String
) {
    init {
        // Update TIS value based on prefill
        selection.value?.let(labelExtractor)?.let {
            textInputState.update(it)
        }
    }

    companion object

    fun ifSelected(
        lambda: (T) -> Unit
    ) {
        selection.value?.let(lambda)
    }
}