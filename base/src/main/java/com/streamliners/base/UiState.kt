package com.ambikaparts.android.base

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

sealed class UiState<T> {
    class New<T>: UiState<T>()
    class Loading<T>: UiState<T>()
    class Data<T>(
        val data: T
    ): UiState<T>()
}

fun <T> loadingStateOf(): MutableState<UiState<T>> {
    return mutableStateOf(UiState.New())
}

fun <T> MutableState<UiState<T>>.update(data: T) {
    value = UiState.Data(data)
}

@Composable
fun <T> MutableState<UiState<T>>.whenNotLoaded(
    content: @Composable () -> Unit
) {
    if (value !is UiState.Data<T>) {
        content()
    }
}

@Composable
fun <T> MutableState<UiState<T>>.onLoaded(
    content: @Composable (data: T) -> Unit
) {
    (value as? UiState.Data<T>)?.let {
        content(it.data)
    }
}

fun <T> MutableState<UiState<T>>.ifNotLoaded(
    lambda: () -> Unit
) {
    if (value is UiState.New) lambda()
}

fun <T> MutableState<UiState<T>>.value(): T {
    return (value as UiState.Data<T>).data
}