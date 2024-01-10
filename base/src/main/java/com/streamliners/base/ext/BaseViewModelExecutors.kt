package com.streamliners.base.ext

import androidx.lifecycle.viewModelScope
import com.streamliners.base.BaseViewModel
import com.streamliners.base.exception.OfflineException
import com.streamliners.base.uiEvent.UiEvent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun BaseViewModel.execute(
    showLoadingDialog: Boolean = true,
    networkCheck: Boolean = true,
    allowRetry: Boolean = networkCheck,
    postExecution: (suspend CoroutineScope.() -> Unit)? = null,
    lambda: suspend CoroutineScope.() -> Unit
) {

    viewModelScope.launch(customContext) {

        if (networkCheck && !isConnected()) {
            throw OfflineException()
        }

        retryLambda = if (allowRetry) fun () {
            execute(
                showLoadingDialog = showLoadingDialog,
                networkCheck = networkCheck,
                allowRetry = true,
                postExecution = postExecution,
                lambda = lambda
            )
        } else null

        if (showLoadingDialog) uiEventFlow.emit(UiEvent.ShowLoadingDialog())
        lambda()
        if (showLoadingDialog) uiEventFlow.emit(UiEvent.HideLoadingDialog)

        if (postExecution != null) postExecution()
    }
}

suspend fun BaseViewModel.executeOnMain(lambda: suspend () -> Unit) {
    withContext(Dispatchers.Main) { lambda() }
}

suspend fun BaseViewModel.showingLoader(lambda: suspend () -> Unit) {
    showLoader()
    lambda()
    hideLoader()
}