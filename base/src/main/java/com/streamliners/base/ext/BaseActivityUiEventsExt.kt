package com.streamliners.base.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import com.streamliners.base.BaseActivity
import com.streamliners.base.uiEvent.UiEvent
import com.streamliners.base.uiEvent.UiEvent.ShowToast.Duration
import com.streamliners.base.uiEvent.UiEvent.ShowToast.Duration.Short

internal fun BaseActivity.handleUiEvent(event: UiEvent) {
    when(event) {
        is UiEvent.ShowLoadingDialog -> {
            showLoadingDialog(event)
        }
        UiEvent.HideLoadingDialog -> {
            hideLoader()
        }
        is UiEvent.ShowMessageDialog -> {
            showMessageDialog(event)
        }
        is UiEvent.ShowErrorDialog -> {
            showMessageDialog(
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
                            dismissOnClick = true,
                            handler = it
                        )
                    },
                    neutralButton = if (event.showCopyButton) UiEvent.DialogButton(
                        label = "COPY",
                        dismissOnClick = true
                    ) {
                        copyText(event.message, "ERROR")
                    } else null
                )
            )
        }
        is UiEvent.ShowToast -> {
            showToast(event.message, event.duration)
        }
        is UiEvent.LoggedOut -> {
            showMessageDialog("Login required", "You have been logged out, please login again!")
            logout()
        }
    }
}

fun BaseActivity.showLoader(message: String? = null) =
    showLoadingDialog(UiEvent.ShowLoadingDialog(message))

fun BaseActivity.showToast(
    message: String,
    duration: Duration = Short
) {
    lastToast?.cancel()
    val length = when (duration) {
        Short -> Toast.LENGTH_SHORT
        Duration.Long -> Toast.LENGTH_LONG
    }
    Toast.makeText(this, message, length).apply {
        lastToast = this
        show()
    }
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
