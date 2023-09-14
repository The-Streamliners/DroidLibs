package com.streamliners.base.uiState

sealed class UiState<T> {

    class New<T>: UiState<T>()

    class Loading<T>: UiState<T>()

    class Data<T>(
        val data: T
    ): UiState<T>()
}