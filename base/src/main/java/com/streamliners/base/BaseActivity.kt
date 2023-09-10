package com.streamliners.base

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.streamliners.base.uiEvent.UiEvent
import com.streamliners.base.uiEvent.UiEvent.*
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity: FragmentActivity() {

    val loadingDialogState = mutableStateOf<ShowLoadingDialog?>(null)
    val otherUiEventsState = mutableStateListOf<UiEvent>()

    private var noOfLoadingDialogs = 0

    val defaultExceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            lifecycleScope.launch(Dispatchers.Main) {
                hideLoadingDialog()
                onExceptionOccurred(throwable)
            }
        }
    }

    fun onExceptionOccurred(e: Throwable) {
        e.printStackTrace()
        handleEvent(UiEvent.forException(e))
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



    /* UI Events */

    val uiEventFlow = MutableSharedFlow<UiEvent>()

    init {
        lifecycleScope.launch {
            uiEventFlow.collect { event ->
                handleEvent(event)
            }
        }
    }

    private var lastToast: Toast? = null

    private fun handleEvent(event: UiEvent) {
        when(event) {
            is ShowLoadingDialog -> {
                showLoadingDialog(event)
            }
            HideLoadingDialog -> {
                hideLoadingDialog()
            }
            is ShowMessageDialog -> {
                otherUiEventsState.add(event)
            }
            is ShowErrorDialog -> {
                otherUiEventsState.add(
                    ShowMessageDialog(
                        title = event.title,
                        message = event.message,
                        isCancellable = !event.isCritical,
                        positiveButton = DialogButton("OKAY") {
                            if (event.isCritical) finish()
                        },
                        negativeButton = event.onRetryClick?.let {
                            DialogButton(
                                label = "RETRY",
                                handler = {
                                    otherUiEventsState.removeAt(
                                        otherUiEventsState.lastIndex
                                    )
                                    it()
                                }
                            )
                        },
                        neutralButton = if (event.showCopyButton) DialogButton("COPY") {
                            copyText(event.message)
                        } else null
                    )
                )
            }
            is ShowToast -> {
                lastToast?.cancel()
                Toast.makeText(this@BaseActivity, event.message, Toast.LENGTH_SHORT).apply {
                    lastToast = this
                    show()
                }
            }
            is LoggedOut -> {
                showMessageDialog("Login required", "You have been logged out, please login again!")
                logout()
            }
        }
    }

    abstract fun logout()

    fun showMessageDialog(event: ShowMessageDialog) {
        otherUiEventsState.add(event)
    }

    private val showLock = Semaphore(1)

    fun showLoadingDialog(event: ShowLoadingDialog = ShowLoadingDialog()) {
        lifecycleScope.launch {
            showLock.acquire()
            loadingDialogState.value = event
            noOfLoadingDialogs++
            debugLog("SHOW $noOfLoadingDialogs", "ECBLDS")
            showLock.release()
        }
    }

    private val hideLock = Semaphore(1)

    fun hideLoadingDialog() {
        lifecycleScope.launch {
            hideLock.acquire()
            if (noOfLoadingDialogs >= 1) {
                debugLog("HIDE $noOfLoadingDialogs", "ECBLDS")
                noOfLoadingDialogs--
                if (noOfLoadingDialogs == 0) {
                    loadingDialogState.value = null
                }
            }
            hideLock.release()
        }
    }

    fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    private fun copyText(message: String) {
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager? ?: run {
            Toast.makeText(this, "Unable to copy!", Toast.LENGTH_SHORT).show()
            return
        }
        val clip = ClipData.newPlainText("ERROR", message)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this, "Copied!", Toast.LENGTH_SHORT).show()
    }

    fun showMessageDialog(title: String, message: String) {
        handleEvent(
            ShowMessageDialog(
                title = title,
                message = message
            )
        )
    }

}