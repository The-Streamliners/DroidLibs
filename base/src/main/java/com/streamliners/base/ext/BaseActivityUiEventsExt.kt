package com.streamliners.base.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.streamliners.base.BaseActivity
import com.streamliners.base.uiEvent.UiEvent

internal fun BaseActivity.handleUiEvent(event: UiEvent) {
    when(event) {
        is UiEvent.ShowLoadingDialog -> {
            showLoadingDialog(event)
        }
        UiEvent.HideLoadingDialog -> {
            hideLoadingDialog()
        }
        is UiEvent.ShowMessageDialog -> {
            otherUiEventsState.add(event)
        }
        is UiEvent.ShowErrorDialog -> {
            otherUiEventsState.add(
                UiEvent.ShowMessageDialog(
                    title = event.title,
                    message = event.message,
                    isCancellable = !event.isCritical,
                    positiveButton = UiEvent.DialogButton("OKAY") {
                        if (event.isCritical) finish()
                    },
                    negativeButton = event.onRetryClick?.let {
                        UiEvent.DialogButton(
                            label = "RETRY",
                            handler = {
                                otherUiEventsState.removeAt(
                                    otherUiEventsState.lastIndex
                                )
                                it()
                            }
                        )
                    },
                    neutralButton = if (event.showCopyButton) UiEvent.DialogButton("COPY") {
                        copyText(event.message, "ERROR")
                    } else null
                )
            )
        }
        is UiEvent.ShowToast -> {
            lastToast?.cancel()
            Toast.makeText(this, event.message, Toast.LENGTH_SHORT).apply {
                lastToast = this
                show()
            }
        }
        is UiEvent.LoggedOut -> {
            showMessageDialog("Login required", "You have been logged out, please login again!")
            logout()
        }
    }
}

fun BaseActivity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun BaseActivity.showMessageDialog(event: UiEvent.ShowMessageDialog) {
    otherUiEventsState.add(event)
}

fun BaseActivity.showMessageDialog(title: String, message: String) {
    showMessageDialog(
        UiEvent.ShowMessageDialog(
            title = title,
            message = message
        )
    )
}

fun BaseActivity.copyText(message: String, label: String = "Text") {
    val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager? ?: run {
        Toast.makeText(this, "Unable to copy!", Toast.LENGTH_SHORT).show()
        return
    }
    val clip = ClipData.newPlainText(label, message)
    clipboard.setPrimaryClip(clip)
    Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show()
}
