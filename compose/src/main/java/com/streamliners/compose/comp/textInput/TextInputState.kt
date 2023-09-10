package com.streamliners.compose.comp.textInput

import androidx.compose.runtime.MutableState
import com.streamliners.compose.comp.textInput.InputType.*

enum class InputType { TEXT, MOBILE_NUMBER, NUMBER, EMAIL }

data class TextInputState(
    val label: String,
    val value: String,
    val error: String?,
    val inputType: InputType,
    val optional: Boolean = false
) {
    companion object {
        fun prefill(
            label: String,
            value: String,
            inputType: InputType = TEXT,
            optional: Boolean = false
        ) = TextInputState(label, value, null, inputType, optional)

        fun empty(
            label: String,
            inputType: InputType = TEXT,
            optional: Boolean = false
        ) = TextInputState(label, "", null, inputType, optional)

        fun validateAll(vararg states: MutableState<TextInputState>): Boolean {
            states.forEach { it.value = it.value.validate() }
            return states.all { !it.value.isError() }
        }

        fun validateAndGetErrors(vararg states: MutableState<TextInputState>): List<String> {
            states.forEach { it.value = it.value.validate() }
            return states.mapNotNull { it.value.error }
        }
    }

    fun update(newValue: String): TextInputState {
        if (!optional && newValue.isBlank()) {
            return copy(value = newValue, error = "${label()} can't be blank!")
        }

        return copy(value = newValue, error = null)
    }

    fun validate(newValue: String, maxLength: Int): TextInputState {
        if (newValue.length > maxLength) {
            if (inputType == MOBILE_NUMBER || inputType == NUMBER) return this
            return copy(value = newValue, error = "Max length limit exceeded!")
        }

        if (!optional && newValue.isBlank()) {
            return copy(value = newValue, error = "Required!")
        }

        if (inputType == MOBILE_NUMBER || inputType == NUMBER) {
            return copy(value = newValue.filter { it.isDigit() }, error = null)
        }

        return copy(value = newValue, error = null)
    }

    fun label(): String {
        return label.replace(" *", "")
    }

    fun validate(): TextInputState {
        if (!optional && value.isBlank())
            return copy(error = "Required!")
        if (inputType == MOBILE_NUMBER && value.length < 10)
            return copy(error = "Invalid Mobile Number!")
        if (inputType == EMAIL && !value.matches(Regex("^\\w+@\\w+.\\w+$")))
            return copy(error = "Invalid E-mail Address!")
        return this
    }

    fun isError() = error != null
}