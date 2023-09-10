package com.streamliners.compose.comp.textInput.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.streamliners.compose.comp.textInput.TextInputState
import com.streamliners.compose.comp.textInput.dialog.TextInputDialogState.Hidden

sealed class TextInputDialogState {
    object Hidden: TextInputDialogState()
    class Visible(
        val title: String,
        val input: MutableState<TextInputState>,
        val maxLength: Int,
        val submitButtonLabel: String,
        val submit: (String) -> Unit
    ): TextInputDialogState()
}

@Composable
fun rememberTextInputDialogState(): MutableState<TextInputDialogState> {
    return remember {
        mutableStateOf(Hidden)
    }
}