package com.streamliners.base.ext

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.streamliners.base.BaseActivity
import com.streamliners.base.BaseViewModel

@Composable
inline fun <reified T : BaseViewModel> BaseActivity.baseViewModel(): T {
    return hiltViewModel<T>().apply {
        this.uiEventFlow = this@baseViewModel.uiEventFlow
        this.showDescriptiveErrorDialogs = this@baseViewModel.showDescriptiveErrorDialogs
        onExceptionOccurred = this@baseViewModel::onExceptionOccurred
        isConnected = ::isConnected
    }
}