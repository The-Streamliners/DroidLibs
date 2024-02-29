package com.streamliners.compose.comp.textInput.state

import com.streamliners.compose.comp.textInput.config.InputConfig
import com.streamliners.compose.comp.textInput.config.text

data class TextInputState(
    val label: String,
    val supportingText: String? = null,
    val value: String = "",
    val error: String? = null,
    val inputConfig: InputConfig = InputConfig.text()
) {
    companion object
}