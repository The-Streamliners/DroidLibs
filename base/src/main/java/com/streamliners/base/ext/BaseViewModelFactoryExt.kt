package com.streamliners.base.ext

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.streamliners.base.BaseActivity
import com.streamliners.base.BaseViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
inline fun <reified T : BaseViewModel> BaseActivity.baseViewModel(): T {
    return viewModel<T>().apply {
        this.uiEventFlow = this@baseViewModel.uiEventFlow
        this.showDescriptiveErrorDialogs = this@baseViewModel.debugMode
        onExceptionOccurred = this@baseViewModel::onExceptionOccurred
        isConnected = ::isConnected
    }
}

@Composable
inline fun <reified T : BaseViewModel> BaseActivity.hiltBaseViewModel(): T {
    return hiltViewModel<T>().apply {
        this.uiEventFlow = this@hiltBaseViewModel.uiEventFlow
        this.showDescriptiveErrorDialogs = this@hiltBaseViewModel.debugMode
        onExceptionOccurred = this@hiltBaseViewModel::onExceptionOccurred
        isConnected = ::isConnected
    }
}

@Composable
inline fun <reified T : BaseViewModel> BaseActivity.koinBaseViewModel(): T {
    return koinViewModel<T>().apply {
        this.uiEventFlow = this@koinBaseViewModel.uiEventFlow
        this.showDescriptiveErrorDialogs = this@koinBaseViewModel.debugMode
        onExceptionOccurred = this@koinBaseViewModel::onExceptionOccurred
        isConnected = ::isConnected
    }
}