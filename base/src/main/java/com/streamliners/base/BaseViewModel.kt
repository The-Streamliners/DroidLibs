package com.streamliners.base

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streamliners.base.uiEvent.UiEvent
import com.streamliners.base.uiEvent.UiEvent.DialogButton
import com.streamliners.base.uiEvent.UiEvent.HideLoadingDialog
import com.streamliners.base.uiEvent.UiEvent.ShowLoadingDialog
import com.streamliners.base.uiEvent.UiEvent.ShowMessageDialog
import com.streamliners.base.uiEvent.UiEvent.ShowToast
import com.streamliners.base.helper.NetworkConnectivityHelper
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class BaseViewModel(
    private val networkConnectivityHelper: NetworkConnectivityHelper? = null
) : ViewModel() {

    
    /* ----------------- * UiEvents * ----------------- */
    
    lateinit var uiEventFlow: MutableSharedFlow<UiEvent>
    var retryLambda: (() -> Unit)? = null

    val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        onExceptionOccurred(throwable)
    }

    fun onExceptionOccurred(throwable: Throwable) {
        viewModelScope.launch {
            throwable.printStackTrace()

            val uiEvent = UiEvent.forException(throwable, retryLambda)
            retryLambda = null

            uiEventFlow.emit(HideLoadingDialog)
            uiEventFlow.emit(uiEvent)
        }
    }

    val customContext = Dispatchers.IO + exceptionHandler

    fun emitUiEvent(uiEvent: UiEvent) {
        viewModelScope.launch {
            uiEventFlow.emit(uiEvent)
        }
    }

    fun executeShowingLoadingDialogAndHandlingError(
        networkCheck: Boolean = true,
        postExecution: (suspend CoroutineScope.() -> Unit)? = null,
        lambda: suspend CoroutineScope.() -> Unit
    ) {

        viewModelScope.launch(customContext) {

            if (networkCheck) {
                retryLambda = {
                    executeShowingLoadingDialogAndHandlingError(true, postExecution, lambda)
                }
                networkConnectivityHelper?.let {
                    if (!it.isConnected()) throw OfflineException()
                } ?: error("networkConnectivityHelper not injected in ${this.javaClass.name}")
            } else {
                retryLambda = null
            }

            uiEventFlow.emit(ShowLoadingDialog())
            lambda()
            uiEventFlow.emit(HideLoadingDialog)

            if (postExecution != null) postExecution()
        }
    }

    fun executeHandlingError(lambda: suspend () -> Unit) {
        retryLambda = null
        viewModelScope.launch(customContext) {
            lambda()
        }
    }

    fun showFailureMessage(message: String) {
        showMessageDialog(
            title = "Failure",
            message = message
        )
    }

    fun showMessageDialog(title: String, message: String) {
        emitUiEvent(
            ShowMessageDialog(
                title = title,
                message = message
            )
        )
    }

    fun showConfirmationDialog(title: String, message: String, onConfirm: () -> Unit) {
        emitUiEvent(
            ShowMessageDialog(
                title = title,
                message = message,
                positiveButton = DialogButton("YES", handler = onConfirm),
                negativeButton = DialogButton("CANCEL") {}
            )
        )
    }

    fun showToast(message: String) {
        emitUiEvent(
            ShowToast(
                message = message
            )
        )
    }

    fun showLoader(message: String? = null) {
        viewModelScope.launch { uiEventFlow.emit(ShowLoadingDialog(message)) }
    }

    fun hideLoader() {
        viewModelScope.launch { uiEventFlow.emit(HideLoadingDialog) }
    }

    suspend fun executeOnMain(lambda: suspend () -> Unit) {
        withContext(Dispatchers.Main) { lambda() }
    }
}

@Composable
inline fun <reified T : BaseViewModel> BaseActivity.baseViewModel(): T {
    return hiltViewModel<T>().apply {
        this.uiEventFlow = this@baseViewModel.uiEventFlow
    }
}