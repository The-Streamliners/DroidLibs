package com.streamliners.base.taskState.comp

import androidx.compose.runtime.Composable
import com.streamliners.base.taskState.Task
import com.streamliners.base.taskState.TaskState

@Composable
fun <T> Task<T>.whenNotLoaded(
    content: @Composable () -> Unit
) {
    if (value !is TaskState.Data<T>) {
        content()
    }
}

@Composable
fun <T> Task<T>.whileLoading(
    content: @Composable () -> Unit
) {
    if (value is TaskState.Loading<T>) {
        content()
    }
}

@Composable
fun <T> Task<T>.whenNew(
    content: @Composable () -> Unit
) {
    if (value is TaskState.New<T>) {
        content()
    }
}

@Composable
fun <T> Task<T>.whenLoading(
    content: @Composable () -> Unit
) {
    if (value is TaskState.Loading<T>) {
        content()
    }
}

@Composable
fun <T> Task<T>.whenNotLoading(
    content: @Composable () -> Unit
) {
    if (value !is TaskState.Loading<T>) {
        content()
    }
}

@Composable
fun <T> Task<T>.whenLoaded(
    content: @Composable (data: T) -> Unit
) {
    (value as? TaskState.Data<T>)?.let {
        content(it.data)
    }
}

@Composable
fun <T> Task<T>.whenError(
    content: @Composable (error: String) -> Unit
) {
    (value as? TaskState.Error<T>)?.let {
        content(it.error)
    }
}

@Composable
fun <T> Task<T>.ui(
    whenNew: @Composable () -> Unit = { },
    whenLoading: @Composable () -> Unit = { },
    whenLoaded: @Composable (data: T) -> Unit = { },
    whenError: @Composable (error: String) -> Unit = { }
) {
    when (val state = value) {
        is TaskState.New -> whenNew()
        is TaskState.Loading -> whenLoading()
        is TaskState.Data -> whenLoaded(state.data)
        is TaskState.Error -> whenError(state.error)
    }
}