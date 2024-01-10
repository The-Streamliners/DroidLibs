package com.streamliners.base.uiState

sealed class TaskState<T> {

    class New<T>: TaskState<T>()

    class Loading<T>: TaskState<T>()

    class Data<T>(
        val data: T
    ): TaskState<T>()
}