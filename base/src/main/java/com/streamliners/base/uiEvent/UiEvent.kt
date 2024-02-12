package com.streamliners.base.uiEvent

import com.streamliners.base.exception.BusinessException
import com.streamliners.base.exception.BusinessException.Level
import com.streamliners.base.exception.LoggedOutException
import com.streamliners.base.exception.OfflineException
import com.streamliners.base.uiEvent.UiEvent.ShowToast.Duration.Short
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

sealed class UiEvent {

    class ShowToast(
        val message: String,
        val duration: Duration = Short
    ): UiEvent() {
        enum class Duration { Short, Long }
    }

    class ShowLoadingDialog(val message: String? = null): UiEvent()

    data object HideLoadingDialog: UiEvent()

    class ShowMessageDialog(
        val title: String,
        val message: String,
        val isCancellable: Boolean = true,
        val positiveButton: DialogButton = DialogButton("OKAY") {},
        val negativeButton: DialogButton? = null,
        val neutralButton: DialogButton? = null,
    ): UiEvent()

    class DialogButton(
        val label: String,
        val dismissOnClick: Boolean = false,
        val handler: () -> Unit = {}
    )

    class ShowErrorDialog(
        val title: String,
        val message: String,
        val isCritical: Boolean = false,
        val onRetryClick: (() -> Unit)? = null,
        val showCopyButton: Boolean = false
    ): UiEvent()

    class LoggedOut(
        exception: LoggedOutException
    ): UiEvent()

    companion object {

        fun forException(
            throwable: Throwable,
            retryLambda: (() -> Unit)? = null,
            showDescriptiveErrorDialogs: Boolean,
            onExceptionOccurred: (Throwable) -> Unit
        ): UiEvent {

            var error = throwable

            /* Slow internet connection errors */
            if (error is SocketTimeoutException ||
                error is ConnectException ||
                error is UnresolvedAddressException ||
                error is UnknownHostException ||
                error is OfflineException
            ) {
                error = BusinessException("Slow / No internet connection detected. Please try again later!")
            }

            return when {
                error is LoggedOutException -> {
                    LoggedOut(error)
                }

                error is BusinessException -> {
                    if (error.level == Level.LOW) {
                        ShowToast("Failure: ${error.message}")
                    } else {
                        ShowErrorDialog(
                            title = "Failure",
                            message = error.message,
                            isCritical = error.level == Level.HIGH,
                            showCopyButton = false,
                            onRetryClick = retryLambda
                        )
                    }
                }

                showDescriptiveErrorDialogs -> {
                    ShowErrorDialog(
                        title = "Internal Failure",
                        message = "${error.message}\n\n${error.stackTraceToString()}",
                        isCritical = false,
                        showCopyButton = true,
                        onRetryClick = retryLambda
                    )
                }

                else -> {
                    onExceptionOccurred(error)
                    ShowErrorDialog(
                        title = "Failure",
                        message = "Unexpected error occurred!",
                        isCritical = false,
                        showCopyButton = false,
                        onRetryClick = retryLambda
                    )
                }
            }
        }
    }
}