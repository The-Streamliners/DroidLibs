package com.streamliners.compose.comp.textInput.state

import androidx.compose.runtime.MutableState

fun TextInputState.hasError() = error != null

fun MutableState<TextInputState>.value(trim: Boolean = true): String {
    return if (trim)
        value.value.trim()
    else
        value.value
}

fun MutableState<TextInputState>.nullableValue() = value.value.trim().ifBlank { null }

fun MutableState<TextInputState>.hasValidInput(): Boolean {
    validate()
    return !value.hasError()
}

fun MutableState<TextInputState>.ifValidInput(
    lambda: (String) -> Unit
) {
    validate()
    if (!value.hasError()) {
        lambda(value())
    }
}

fun MutableState<TextInputState>.changeLabel(label: String) {
    value = value.copy(label = label)
}

fun MutableState<TextInputState>.update(newValue: String) {
    value = value.copy(value = newValue, error = null)
}