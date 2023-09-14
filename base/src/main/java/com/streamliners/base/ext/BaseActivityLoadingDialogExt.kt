package com.streamliners.base.ext

import androidx.lifecycle.lifecycleScope
import com.streamliners.base.BaseActivity
import com.streamliners.base.uiEvent.UiEvent
import kotlinx.coroutines.launch

fun BaseActivity.showLoadingDialog(event: UiEvent.ShowLoadingDialog = UiEvent.ShowLoadingDialog()) {
    lifecycleScope.launch {
        showLock.acquire()
        loadingDialogState.value = event
        noOfLoadingDialogs++
        showLock.release()
    }
}

fun BaseActivity.hideLoadingDialog() {
    lifecycleScope.launch {
        hideLock.acquire()
        if (noOfLoadingDialogs >= 1) {
            noOfLoadingDialogs--
            if (noOfLoadingDialogs == 0) {
                loadingDialogState.value = null
            }
        }
        hideLock.release()
    }
}