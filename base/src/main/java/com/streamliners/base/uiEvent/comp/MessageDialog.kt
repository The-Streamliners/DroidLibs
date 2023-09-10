package com.streamliners.base.uiEvent.comp

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.streamliners.base.uiEvent.UiEvent

@ExperimentalComposeUiApi
@ExperimentalMaterial3Api
@Composable
fun MessageDialog(
    state: UiEvent.ShowMessageDialog,
    dismiss: () -> Unit
) {
    AlertDialog(
        modifier = Modifier
            .padding(28.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        onDismissRequest = dismiss,
        title = {
            Text(text = state.title)
        },
        text = {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                text = state.message
            )
        },
        confirmButton = {
            TextButton(
                onClick = {
                    state.positiveButton.apply {
                        handler()
                        if (state.isCancellable || dismissOnClick) dismiss()
                    }
                }
            ) {
                Text(state.positiveButton.label, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            }
        },
        dismissButton = if (state.negativeButton != null) { {
            TextButton(
                onClick = {
                    state.negativeButton.apply {
                        handler()
                        if (dismissOnClick) dismiss()
                    }
                }
            ) {
                Text(
                    state.negativeButton.label,
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        } } else null
    )
}