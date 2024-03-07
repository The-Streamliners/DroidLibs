package com.streamliners.compose.android.ext

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.streamliners.compose.comp.textInput.dialog.TextInputDialogState

fun ViewModel.textInputDialogState(): MutableState<TextInputDialogState> {
    return mutableStateOf(TextInputDialogState.Hidden)
}