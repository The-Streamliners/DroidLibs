package com.streamliners.base.uiState

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// Factory functions ----------------------------------

fun <T> taskStateOf(): MutableState<TaskState<T>> {
    return mutableStateOf(TaskState.New())
}

fun <T> loadingUiStateOf(): MutableState<TaskState<T>> {
    return mutableStateOf(TaskState.New())
}


// Data loading functions -----------------------------

fun <T> MutableState<TaskState<T>>.update(data: T) {
    value = TaskState.Data(data)
}

fun <T> MutableState<TaskState<T>>.ifNotLoaded(
    lambda: () -> Unit
) {
    if (value is TaskState.New) lambda()
}

suspend fun <T> MutableState<TaskState<T>>.load(
    lambda: suspend () -> T
) {
    if (value is TaskState.New) {
        value = TaskState.Loading()
        update(lambda())
    }
}

suspend fun <T> MutableState<TaskState<T>>.reLoad(
    lambda: suspend () -> T
) {
    value = TaskState.Loading()
    update(lambda())
}


// Data access functions ------------------------------

fun <T> MutableState<TaskState<T>>.value(): T {
    return (value as TaskState.Data<T>).data
}

fun <T> MutableState<TaskState<T>>.valueNullable(): T? {
    return (value as? TaskState.Data<T>)?.data
}