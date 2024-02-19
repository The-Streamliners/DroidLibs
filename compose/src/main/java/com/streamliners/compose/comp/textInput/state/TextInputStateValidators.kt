package com.streamliners.compose.comp.textInput.state

import androidx.compose.runtime.MutableState
import androidx.compose.ui.text.input.KeyboardType

// Value validators ----------------------------------

fun MutableState<TextInputState>.preValidateAndUpdate(newValue: String) {
    value = with(value) {
        if (newValue.length > inputConfig.maxLength) {
            if (inputConfig.strictMaxLengthCheck) return
            return@with copy(
                value = newValue, error = "Max length limit exceeded!"
            )
        }

        if (!inputConfig.optional && newValue.isBlank()) {
            return@with copy(
                value = newValue, error = "Required!"
            )
        }

        if (inputConfig.keyboardType == KeyboardType.Number) {
            return@with copy(
                value = newValue.filter { it.isDigit() }, error = null
            )
        }

        copy(
            value = newValue, error = null
        )
    }
}

fun MutableState<TextInputState>.validate() {
    value = with(value) {
        if (!inputConfig.optional && value.isBlank()) {
            return@with copy(
                error = "Required!"
            )
        }

        if (value.length < inputConfig.minLength) {
            val type = if (inputConfig.keyboardType in listOf(KeyboardType.Number, KeyboardType.Decimal))
                "digits"
            else
                "characters"
            return@with copy(
                error = "Minimum length is ${inputConfig.minLength} $type!"
            )
        }

        inputConfig.regexValidation?.let {
            if (!it.regex.matches(value)) {
                return@with copy(
                    error = it.errorMessage ?: "Invalid input!"
                )
            }
        }

        this
    }
}


// Multiple TIS validators ---------------------------

fun TextInputState.Companion.allHaveValidInputs(
    vararg states: MutableState<TextInputState>
): Boolean {
    states.forEach { it.validate() }
    return states.all { !it.value.hasError() }
}

fun TextInputState.Companion.getErrors(
    vararg states: MutableState<TextInputState>
): List<String> {
    states.forEach { it.validate() }
    return states.mapNotNull { state ->
        state.value.error?.let {
            "${state.value.label} : $it"
        }
    }
}