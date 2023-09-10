package com.streamliners.compose.comp.textInput

import androidx.compose.runtime.MutableState

fun MutableState<TextInputState>.value(trim: Boolean = true): String {
    return if (trim)
        value.value.trim()
    else
        value.value
}

fun MutableState<TextInputState>.nullableValue() = value.value.trim().ifBlank { null }

fun MutableState<TextInputState>.update(newValue: String) {
    value = value.update(newValue)
}

fun MutableState<TextInputState>.changeLabel(label: String) {
    value = value.copy(label = label)
}