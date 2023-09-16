package com.streamliners.compose.comp.spinner.state

import androidx.compose.runtime.mutableStateOf
import com.streamliners.compose.comp.textInput.state.TextInputState

fun <T> SpinnerState.Companion.create(
    label: String,
    prefill: T? = null,
    labelExtractor: (T) -> String
): SpinnerState<T> {
    return SpinnerState(
        mutableStateOf(prefill),
        mutableStateOf(
            if (prefill != null) {
                TextInputState(
                    label = label,
                    value = labelExtractor(prefill)
                )
            } else {
                TextInputState(label)
            }
        ),
        labelExtractor
    )
}

fun SpinnerState.Companion.ofString(
    label: String,
    prefill: String? = null
): SpinnerState<String> {
    return SpinnerState(
        mutableStateOf(prefill),
        mutableStateOf(
            if (prefill != null) {
                TextInputState(
                    label = label,
                    value = prefill
                )
            } else {
                TextInputState(label)
            }
        ),
        labelExtractor = { it }
    )
}