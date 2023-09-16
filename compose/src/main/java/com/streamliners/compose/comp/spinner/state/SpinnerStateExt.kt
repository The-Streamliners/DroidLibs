package com.streamliners.compose.comp.spinner.state

import com.streamliners.compose.comp.textInput.state.update
import com.streamliners.compose.comp.textInput.state.validate

fun <T> SpinnerState<T>.value(validate: Boolean = false): T? {
    if (validate) textInputState.validate()
    return state.value
}

fun <T> SpinnerState<T>.updateValue(value: T) {
    state.value = value
    textInputState.update(labelExtractor(value))
}

fun <T> SpinnerState<T>.clear() {
    state.value = null
    textInputState.value = textInputState.value.copy(
        value = ""
    )
}