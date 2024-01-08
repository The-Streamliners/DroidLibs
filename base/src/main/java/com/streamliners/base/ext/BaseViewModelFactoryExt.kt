package com.streamliners.base.ext

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.streamliners.base.BaseActivity
import com.streamliners.base.BaseViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun <reified T : BaseViewModel> BaseActivity.hiltBaseViewModel(): T {
    return hiltViewModel<T>().apply {
        this.uiEventFlow = this@hiltBaseViewModel.uiEventFlow
        this.showDescriptiveErrorDialogs = this@hiltBaseViewModel.showDescriptiveErrorDialogs
        onExceptionOccurred = this@hiltBaseViewModel::onExceptionOccurred
        isConnected = ::isConnected
    }
}

@Composable
inline fun <reified T : BaseViewModel> BaseActivity.koinBaseViewModel(): T {
    return koinViewModel<T>().apply {
        this.uiEventFlow = this@koinBaseViewModel.uiEventFlow
        this.showDescriptiveErrorDialogs = this@koinBaseViewModel.showDescriptiveErrorDialogs
        onExceptionOccurred = this@koinBaseViewModel::onExceptionOccurred
        isConnected = ::isConnected
    }
}