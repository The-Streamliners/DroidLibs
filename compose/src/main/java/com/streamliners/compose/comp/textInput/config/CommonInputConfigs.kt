package com.streamliners.compose.comp.textInput.config

import androidx.compose.ui.text.input.KeyboardType
import com.streamliners.compose.comp.textInput.config.InputConfig.RegexValidation

fun InputConfig.Companion.text(
    customize: InputConfig.() -> Unit = {}
) = InputConfig(customize)

fun InputConfig.Companion.longText(
    customize: InputConfig.() -> Unit = {}
) = InputConfig {
    singleLine = false
    maxLines = 10
    customize(this)
}

fun InputConfig.Companion.password(
    customize: InputConfig.() -> Unit = {}
) = InputConfig {
    keyboardType = KeyboardType.Password
    customize(this)
}

fun InputConfig.Companion.fixedLengthNumber(
    length: Int,
    customize: InputConfig.() -> Unit = {}
) = InputConfig {
    keyboardType = KeyboardType.Number
    regexValidation = RegexValidation(
        regex = Regex("^\\d{${length}}$"),
        errorMessage = "Input must be $length digits long!"
    )
    maxLength = length
    strictMaxLengthCheck = true
    customize(this)
}

fun InputConfig.Companion.number(
    customize: InputConfig.() -> Unit = {}
) = InputConfig {
    keyboardType = KeyboardType.Number
    customize(this)
}

fun InputConfig.Companion.decimal(
    customize: InputConfig.() -> Unit = {}
) = InputConfig {
    keyboardType = KeyboardType.Decimal
    customize(this)
}

fun InputConfig.Companion.email(
    customize: InputConfig.() -> Unit = {}
) = InputConfig {
    keyboardType = KeyboardType.Email
    regexValidation = RegexValidation(
        Regex("^\\w+@\\w+.\\w+$")
    )
    customize(this)
}