package com.streamliners.base.uiState

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState

@Composable
fun <T> MutableState<TaskState<T>>.whenNotLoaded(
    content: @Composable () -> Unit
) {
    if (value !is TaskState.Data<T>) {
        content()
    }
}

@Composable
fun <T> MutableState<TaskState<T>>.whileLoading(
    content: @Composable () -> Unit
) {
    if (value is TaskState.Loading<T>) {
        content()
    }
}

@Composable
fun <T> MutableState<TaskState<T>>.whenNew(
    content: @Composable () -> Unit
) {
    if (value is TaskState.New<T>) {
        content()
    }
}

@Composable
fun <T> MutableState<TaskState<T>>.whenLoaded(
    content: @Composable (data: T) -> Unit
) {
    (value as? TaskState.Data<T>)?.let {
        content(it.data)
    }
}

@Composable
fun <T> MutableState<TaskState<T>>.ui(
    whenNew: @Composable () -> Unit = { },
    whenLoading: @Composable () -> Unit = { },
    whenLoaded: @Composable (data: T) -> Unit = { }
) {
    when (val state = value) {
        is TaskState.New -> whenNew()
        is TaskState.Loading -> whenLoading()
        is TaskState.Data -> whenLoaded(state.data)
    }
}