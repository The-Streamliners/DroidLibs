package com.streamliners.base.uiEvent

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import com.streamliners.base.BaseActivity
import com.streamliners.base.uiEvent.comp.MessageDialog
import com.streamliners.base.uiEvent.comp.LoadingDialog

@Composable
fun BaseActivity.UiEventDialogs() {
    loadingDialogState.value?.let {
        LoadingDialog(
            state = it,
            hide = { loadingDialogState.value = null }
        )
    }

    otherUiEventsState.forEach {
        if (it is UiEvent.ShowMessageDialog) {
            MessageDialog(
                state = it,
                dismiss = {
                    otherUiEventsState.remove(it)
                }
            )
        }
    }
}