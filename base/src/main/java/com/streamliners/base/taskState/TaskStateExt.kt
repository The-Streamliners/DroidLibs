package com.streamliners.base.taskState

import androidx.compose.runtime.mutableStateOf

// Factory functions ----------------------------------

fun <T> taskStateOf(): Task<T> {
    return mutableStateOf(TaskState.New())
}

fun <T> loadingTaskStateOf(): Task<T> {
    return mutableStateOf(TaskState.Loading())
}


// Data loading functions -----------------------------

fun <T> Task<T>.update(data: T) {
    value = TaskState.Data(data)
}

suspend fun <T> Task<T>.ifNotLoaded(
    lambda: suspend () -> Unit
) {
    if (value !is TaskState.Data) lambda()
}

suspend fun <T> Task<T>.load(
    lambda: suspend () -> T
) {
    ifNotLoaded { reLoad(lambda) }
}

suspend fun <T> Task<T>.reLoad(
    lambda: suspend () -> T
) {
    value = TaskState.Loading()
    try {
        update(lambda())
    } catch (e: Exception) {
        value = TaskState.Error(e.message ?: "Unknown error", e)
        throw e
    }
}


// Data access functions ------------------------------

fun <T> Task<T>.value(): T {
    return (value as TaskState.Data<T>).data
}

fun <T> Task<T>.valueNullable(): T? {
    return (value as? TaskState.Data<T>)?.data
}