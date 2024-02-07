package com.streamliners.base.ext

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import com.streamliners.base.BaseViewModel
import com.streamliners.base.exception.OfflineException
import com.streamliners.base.taskState.TaskState
import com.streamliners.base.uiEvent.UiEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

fun BaseViewModel.execute(
    showLoadingDialog: Boolean = true,
    networkCheck: Boolean = true,
    allowRetry: Boolean = false,
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

fun <T> BaseViewModel.execute(
    task: MutableState<TaskState<T>>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    networkCheck: Boolean = true,
    lambda: suspend () -> T
) {
    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        task.value = TaskState.Error(
            error = throwable.message ?: "Unknown error",
            throwable = throwable
        )
    }

    task.value = TaskState.Loading()
    viewModelScope.launch(dispatcher + exceptionHandler) {
        if (networkCheck && !isConnected()) throw OfflineException()

        task.value = TaskState.Data(lambda())
    }
}

fun <T> BaseViewModel.executeIfNotLoaded(
    task: MutableState<TaskState<T>>,
    dispatcher: CoroutineDispatcher = Dispatchers.IO,
    networkCheck: Boolean = true,
    lambda: suspend () -> T
) {
    if (task.value !is TaskState.Data) execute(task, dispatcher, networkCheck, lambda)
}

suspend fun BaseViewModel.executeOnMain(lambda: suspend () -> Unit) {
    withContext(Dispatchers.Main) { lambda() }
}

suspend fun BaseViewModel.showingLoader(lambda: suspend () -> Unit) {
    showLoader()
    lambda()
    hideLoader()
}