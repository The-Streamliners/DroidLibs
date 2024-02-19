package com.streamliners.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.streamliners.base.uiEvent.UiEvent
import com.streamliners.base.uiEvent.UiEvent.HideLoadingDialog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel() {

    lateinit var onExceptionOccurred: (Throwable) -> Unit
    lateinit var isConnected: () -> Boolean
    var showDescriptiveErrorDialogs = false
    
    lateinit var uiEventFlow: MutableSharedFlow<UiEvent>
    var retryLambda: (() -> Unit)? = null

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleException(throwable)
    }

    val customContext = Dispatchers.IO + exceptionHandler

    open fun init() { }

    private fun handleException(throwable: Throwable) {
        viewModelScope.launch {
            throwable.printStackTrace()

            val uiEvent = UiEvent.forException(
                throwable, retryLambda, showDescriptiveErrorDialogs, onExceptionOccurred
            )
            retryLambda = null

            uiEventFlow.emit(HideLoadingDialog)
            uiEventFlow.emit(uiEvent)
        }
    }
}