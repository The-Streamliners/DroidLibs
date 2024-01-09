package com.streamliners.base.ext

import androidx.lifecycle.viewModelScope
import com.streamliners.base.BaseViewModel
import com.streamliners.base.uiEvent.UiEvent
import kotlinx.coroutines.launch

fun BaseViewModel.emitUiEvent(uiEvent: UiEvent) {
    viewModelScope.launch {
        uiEventFlow.emit(uiEvent)
    }
}

fun BaseViewModel.showToast(message: String) {
    emitUiEvent(
        UiEvent.ShowToast(
            message = message
        )
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

fun BaseViewModel.showMessageDialog(title: String, message: String) {
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

fun BaseViewModel.showConfirmationDialog(title: String, message: String, onConfirm: () -> Unit) {
    emitUiEvent(
        UiEvent.ShowMessageDialog(
            title = title,
            message = message,
            positiveButton = UiEvent.DialogButton("YES", handler = onConfirm),
            negativeButton = UiEvent.DialogButton("CANCEL") {}
        )
    )
}
