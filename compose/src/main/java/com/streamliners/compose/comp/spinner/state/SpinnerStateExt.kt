package com.streamliners.compose.comp.spinner.state

import com.streamliners.compose.comp.textInput.state.update
import com.streamliners.compose.comp.textInput.state.validate

fun <T> SpinnerState<T>.value(validate: Boolean = false): T? {
    if (validate) textInputState.validate()
    return selection.value
}

fun <T> SpinnerState<T>.updateValue(value: T) {
    selection.value = value
    textInputState.update(labelExtractor(value))
}

fun <T> SpinnerState<T>.clear() {
    selection.value = null
    textInputState.value = textInputState.value.copy(
        value = ""
    )
}