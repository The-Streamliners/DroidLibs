package com.streamliners.compose.comp.textInput.dialog

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.dialog.TextInputDialogState.Hidden

sealed class TextInputDialogState {
    data object Hidden: TextInputDialogState()
    class Visible(
        val title: String,
        val input: MutableState<TextInputState>,
        val submitButtonLabel: String = "Submit",
        val submit: (String) -> Unit
    ): TextInputDialogState()
}

@Composable
fun rememberTextInputDialogState(): MutableState<TextInputDialogState> {
    return remember {
        mutableStateOf(Hidden)
    }
}

fun ViewModel.textInputDialogState(): MutableState<TextInputDialogState> {
    return mutableStateOf(Hidden)
}