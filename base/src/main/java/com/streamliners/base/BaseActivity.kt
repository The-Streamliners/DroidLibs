package com.streamliners.base

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.streamliners.base.exception.OfflineException
import com.streamliners.base.ext.handleUiEvent
import com.streamliners.base.ext.hideLoader
import com.streamliners.base.ext.isConnected
import com.streamliners.base.ext.showLoader
import com.streamliners.base.uiEvent.UiEvent
import com.streamliners.base.uiEvent.UiEvent.ShowLoadingDialog
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Semaphore
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity: FragmentActivity() {

    // Executors ------------------------------------------

    private val defaultExceptionHandler by lazy {
        CoroutineExceptionHandler { _, throwable ->
            lifecycleScope.launch(Dispatchers.Main) {
                hideLoader()
                handleException(throwable)
            }
        }
    }

    private fun handleException(e: Throwable) {
        if (debugMode) e.printStackTrace()
        onExceptionOccurred(e)

        handleUiEvent(
            UiEvent.forException(
                throwable = e,
                showDescriptiveErrorDialogs = debugMode,
                onExceptionOccurred = ::onExceptionOccurred
            )
        )
    }

    fun execute(
        showLoadingDialog: Boolean = true,
        networkCheck: Boolean = true,
        dispatchers: CoroutineContext = Dispatchers.IO,
        lambda: suspend CoroutineScope.() -> Unit
    ) {

        lifecycleScope.launch(dispatchers + defaultExceptionHandler) {

            if (networkCheck && !isConnected()) {
                throw OfflineException()
            }

            if (showLoadingDialog) showLoader()
            lambda()
            if (showLoadingDialog) hideLoader()
        }
    }


    // Extendable -----------------------------------------

    abstract var buildType: String
    val debugMode
        get() = buildType == "debug"

    open fun logout() {}
    open fun onExceptionOccurred(e: Throwable) {}

    @Composable
    open fun GeneralLoadingDialog() {

        val size = 150.dp

        Box(
            Modifier
                .size(size)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.surface)
        ) {

            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }


    // UI Events ------------------------------------------

    val uiEventFlow = MutableSharedFlow<UiEvent>()

    init {
        lifecycleScope.launch {
            uiEventFlow.collect(::handleUiEvent)
        }
    }

    val loadingDialogState = mutableStateOf<ShowLoadingDialog?>(null)
    val otherUiEventsState = mutableStateListOf<UiEvent>()

    internal var lastToast: Toast? = null

    internal var noOfLoadingDialogs = 0
    internal val showLock = Semaphore(1)
    internal val hideLock = Semaphore(1)
}