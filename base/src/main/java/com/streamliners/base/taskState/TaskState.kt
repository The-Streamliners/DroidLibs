package com.streamliners.base.taskState

import androidx.compose.runtime.MutableState

typealias Task<T> = MutableState<TaskState<T>>

sealed class TaskState<T> {

    class New<T>: TaskState<T>()

    class Loading<T>: TaskState<T>()

    class Data<T>(
        val data: T
    ): TaskState<T>()

    class Error<T>(
        val error: String,
        val throwable: Throwable
    ): TaskState<T>()
}