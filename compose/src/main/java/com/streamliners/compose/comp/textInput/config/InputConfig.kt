package com.streamliners.compose.comp.textInput.config

import androidx.compose.ui.text.input.KeyboardType

class InputConfig(
    builder: InputConfig.() -> Unit = { }
) {

    var keyboardType: KeyboardType = KeyboardType.Text

    var regexValidation: RegexValidation? = null
    var optional = false

    var minLength = if (optional) 0 else 1
    var maxLength = Int.MAX_VALUE
    var strictMaxLengthCheck = false

    class RegexValidation(
        val regex: Regex,
        val errorMessage: String? = null
    )

    init {
        builder(this)
    }

    companion object
}