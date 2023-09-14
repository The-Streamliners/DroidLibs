package com.streamliners.base.exception

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun debugLog(
    message: String,
    tag: String? = null,
    isError: Boolean = false
) {
    val prefix = tag?.let { "$tag -> " } ?: ""
    if (isError) {
        Log.e("DroidLibs", "$prefix $message")
    } else {
        Log.i("DroidLibs", "$prefix $message")
    }
}

fun defaultScope(
    tag: String? = null,
    onExceptionOccurred: (Throwable) -> Unit = {}
): CoroutineContext {
    return Dispatchers.IO + defaultExceptionHandler(tag, onExceptionOccurred)
}

fun defaultExceptionHandler(
    tag: String? = null,
    onExceptionOccurred: (Throwable) -> Unit = {}
): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, throwable ->
        debugLog(
            message = "ERROR - ${throwable.message}",
            tag = tag,
            isError = true
        )
        throwable.printStackTrace()
        onExceptionOccurred(throwable)
    }
}

fun defaultExecuteHandlingError(
    tag: String? = null,
    onExceptionOccurred: (Throwable) -> Unit = {},
    lambda: suspend CoroutineScope.() -> Unit
) {
    CoroutineScope(
        defaultScope(tag, onExceptionOccurred)
    ).launch {
        lambda()
    }
}