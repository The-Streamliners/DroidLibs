package com.streamliners.compose.comp.spinner

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import com.streamliners.compose.comp.textInput.TextInputState
import com.streamliners.compose.comp.textInput.update

class SelectedSuggestion<T>(
    val state: MutableState<T?>,
    val textInputState: MutableState<TextInputState>,
    val labelExtractor: (T) -> String
) {
    companion object {
        inline fun <reified T> create(
            label: String,
            prefill: T? = null,
            noinline labelExtractor: (T) -> String
        ): SelectedSuggestion<T> {
            return SelectedSuggestion(
                mutableStateOf(prefill),
                mutableStateOf(
                    if (prefill != null)
                        TextInputState.prefill(label, labelExtractor(prefill))
                    else
                        TextInputState.empty(label)
                ),
                labelExtractor
            )
        }
    }

    fun value(validate: Boolean = false): T? {
        if (validate) {
            textInputState.value = textInputState.value.validate()
        }
        return state.value
    }

    fun updateValue(value: T) {
        state.value = value
        textInputState.update(labelExtractor(value))
    }

    fun clear() {
        state.value = null
        textInputState.value = textInputState.value.copy(
            value = ""
        )
    }
}