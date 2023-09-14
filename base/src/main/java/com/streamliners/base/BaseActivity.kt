package com.streamliners.base

import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.streamliners.base.ext.handleUiEvent
import com.streamliners.base.ext.hideLoadingDialog
import com.streamliners.base.ext.showLoadingDialog
import com.streamliners.base.uiEvent.UiEvent
import com.streamliners.base.uiEvent.UiEvent.ShowLoadingDialog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlin.coroutines.CoroutineContext

open class BaseActivity: FragmentActivity() {

    // Executors ------------------------------------------

    private val defaultExceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            lifecycleScope.launch(Dispatchers.Main) {
                hideLoadingDialog()
                this@BaseActivity.handleException(throwable)
            }
        }
    }

    private fun handleException(e: Throwable) {
        e.printStackTrace()
        handleUiEvent(
            UiEvent.forException(
                throwable = e,
                showDescriptiveErrorDialogs = showDescriptiveErrorDialogs,
                onExceptionOccurred = ::onExceptionOccurred
            )
        )
    }

    fun executeHandlingError(
        showLoadingDialog: Boolean = false,
        dispatchers: CoroutineContext = Dispatchers.IO,
        lambda: suspend CoroutineScope.() -> Unit
    ) {
        lifecycleScope.launch(dispatchers + defaultExceptionHandler) {
            if(showLoadingDialog) showLoadingDialog()
            lambda()
            if(showLoadingDialog) hideLoadingDialog()
        }
    }


    // Extendable -----------------------------------------

    var showDescriptiveErrorDialogs = false

    open fun logout() {}
    open fun onExceptionOccurred(e: Throwable) {}


    // UI Events ------------------------------------------

    val uiEventFlow = MutableSharedFlow<UiEvent>()

    init {
        lifecycleScope.launch {
            uiEventFlow.collect { event ->
                handleUiEvent(event)
            }
        }
    }

    val loadingDialogState = mutableStateOf<ShowLoadingDialog?>(null)
    val otherUiEventsState = mutableStateListOf<UiEvent>()

    internal var lastToast: Toast? = null

    internal var noOfLoadingDialogs = 0
    internal val showLock = Semaphore(1)
    internal val hideLock = Semaphore(1)
}