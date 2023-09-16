package com.streamliners.compose.comp.textInput.state

import androidx.compose.runtime.MutableState

fun TextInputState.isError() = error != null

fun TextInputState.isValid() = error == null

fun MutableState<TextInputState>.value(trim: Boolean = true): String {
    return if (trim)
        value.value.trim()
    else
        value.value
}

fun MutableState<TextInputState>.nullableValue() = value.value.trim().ifBlank { null }

fun MutableState<TextInputState>.isValid(): Boolean {
    return value.isValid()
}

fun MutableState<TextInputState>.changeLabel(label: String) {
    value = value.copy(label = label)
}

fun MutableState<TextInputState>.update(newValue: String) {
    value = value.copy(value = newValue, error = null)
}