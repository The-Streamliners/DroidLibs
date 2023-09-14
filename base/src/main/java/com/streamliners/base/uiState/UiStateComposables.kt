package com.streamliners.base.uiState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun <T> MutableState<UiState<T>>.whenNotLoaded(
    content: @Composable () -> Unit
) {
    if (value !is UiState.Data<T>) {
        content()
    }
}

@Composable
fun <T> MutableState<UiState<T>>.whileLoading(
    content: @Composable () -> Unit
) {
    if (value is UiState.Loading<T>) {
        content()
    }
}

@Composable
fun <T> MutableState<UiState<T>>.whenNew(
    content: @Composable () -> Unit
) {
    if (value is UiState.New<T>) {
        content()
    }
}

@Composable
fun <T> MutableState<UiState<T>>.whenLoaded(
    content: @Composable (data: T) -> Unit
) {
    (value as? UiState.Data<T>)?.let {
        content(it.data)
    }
}

@Composable
fun <T> MutableState<UiState<T>>.ui(
    whenNew: @Composable () -> Unit = { },
    whenLoading: @Composable () -> Unit = { },
    whenLoaded: @Composable (data: T) -> Unit = { }
) {
    when (val state = value) {
        is UiState.New -> whenNew()
        is UiState.Loading -> whenLoading()
        is UiState.Data -> whenLoaded(state.data)
    }
}