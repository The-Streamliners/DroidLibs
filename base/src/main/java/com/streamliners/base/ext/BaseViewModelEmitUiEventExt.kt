package com.streamliners.base.ext

import androidx.lifecycle.viewModelScope
import com.streamliners.base.BaseViewModel
import com.streamliners.base.uiEvent.UiEvent
import com.streamliners.base.uiEvent.UiEvent.ShowToast.Duration
import com.streamliners.base.uiEvent.UiEvent.ShowToast.Duration.Short
import kotlinx.coroutines.launch

fun BaseViewModel.emitUiEvent(uiEvent: UiEvent) {
    viewModelScope.launch {
        uiEventFlow.emit(uiEvent)
    }
}

fun BaseViewModel.showToast(
    message: String,
    duration: Duration = Short
) {
    emitUiEvent(
        UiEvent.ShowToast(message, duration)
    )
}

fun BaseViewModel.showLoader(message: String? = null) {
    emitUiEvent(
        UiEvent.ShowLoadingDialog(message)
    )
}

fun BaseViewModel.hideLoader() {
    emitUiEvent(
        UiEvent.HideLoadingDialog
    )
}

fun BaseViewModel.showMessageDialog(
    event: UiEvent.ShowMessageDialog
) {
    emitUiEvent(event)
}

fun BaseViewModel.showMessageDialog(
    title: String,
    message: String
) {
    emitUiEvent(
        UiEvent.ShowMessageDialog(
            title = title,
            message = message
        )
    )
}

fun BaseViewModel.showFailureMessage(message: String) {
    showMessageDialog(
        title = "Failure",
        message = message
    )
}

fun BaseViewModel.showConfirmationDialog(
    title: String,
    message: String,
    confirmButtonLabel: String = "YES",
    onConfirm: () -> Unit
) {
    emitUiEvent(
        UiEvent.ShowMessageDialog(
            title = title,
            message = message,
            positiveButton = UiEvent.DialogButton(confirmButtonLabel, handler = onConfirm),
            negativeButton = UiEvent.DialogButton("CANCEL", true) {}
        )
    )
}
