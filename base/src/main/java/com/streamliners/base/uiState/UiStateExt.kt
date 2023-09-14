package com.streamliners.base.uiState

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

// Factory functions ----------------------------------

fun <T> uiStateOf(): MutableState<UiState<T>> {
    return mutableStateOf(UiState.New())
}

fun <T> loadingUiStateOf(): MutableState<UiState<T>> {
    return mutableStateOf(UiState.New())
}


// Data loading functions -----------------------------

fun <T> MutableState<UiState<T>>.update(data: T) {
    value = UiState.Data(data)
}

fun <T> MutableState<UiState<T>>.ifNotLoaded(
    lambda: () -> Unit
) {
    if (value is UiState.New) lambda()
}

suspend fun <T> MutableState<UiState<T>>.load(
    lambda: suspend () -> T
) {
    if (value is UiState.New) {
        value = UiState.Loading()
        update(lambda())
    }
}

suspend fun <T> MutableState<UiState<T>>.reLoad(
    lambda: suspend () -> T
) {
    value = UiState.Loading()
    update(lambda())
}


// Data access functions ------------------------------

fun <T> MutableState<UiState<T>>.value(): T {
    return (value as UiState.Data<T>).data
}

fun <T> MutableState<UiState<T>>.valueNullable(): T? {
    return (value as? UiState.Data<T>)?.data
}