package com.streamliners.base.uiEvent

import com.streamliners.base.BusinessException
import com.streamliners.base.BusinessExceptionLevel
import com.streamliners.base.LoggedOutException
import com.streamliners.base.OfflineException
import com.streamliners.base.recordException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.nio.channels.UnresolvedAddressException

sealed class UiEvent {

    class ShowToast(val message: String): UiEvent()

    class ShowLoadingDialog(val message: String? = null): UiEvent()

    object HideLoadingDialog: UiEvent()

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
        val handler: () -> Unit
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
            retryLambda: (() -> Unit)? = null
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
                    if (error.level == BusinessExceptionLevel.LOW) {
                        ShowToast("Failure: ${error.message}")
                    } else {
                        ShowErrorDialog(
                            title = "Failure",
                            message = error.message,
                            isCritical = error.level == BusinessExceptionLevel.HIGH,
                            showCopyButton = false,
                            onRetryClick = retryLambda
                        )
                    }
                }

                // TODO : Access app build type from library and branch out accordingly
//                BuildConfig.DEBUG -> {
                else -> {
                    error.printStackTrace()
                    ShowErrorDialog(
                        title = "Internal Failure",
                        message = "${error.message}\n\n${error.stackTraceToString()}",
                        isCritical = false,
                        showCopyButton = true,
                        onRetryClick = retryLambda
                    )
                }

//                else -> {
//                    recordException(error)
//                    ShowErrorDialog(
//                        title = "Failure",
//                        message = "Unexpected error occurred!",
//                        isCritical = false,
//                        showCopyButton = false,
//                        onRetryClick = retryLambda
//                    )
//                }
            }
        }
    }
}