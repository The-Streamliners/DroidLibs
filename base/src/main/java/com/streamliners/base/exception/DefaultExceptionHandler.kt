package com.streamliners.base.exception

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

fun log(
    message: String,
    tag: String? = null,
    isError: Boolean = false,
    buildType: String
) {
    if (buildType != "debug") return
    val prefix = tag?.let { "$tag -> " } ?: ""
    if (isError) {
        Log.e("DroidLibs", "$prefix $message")
    } else {
        Log.i("DroidLibs", "$prefix $message")
    }
}

fun defaultContext(
    tag: String? = null,
    onExceptionOccurred: (Throwable) -> Unit = {},
    buildType: String
): CoroutineContext {
    return Dispatchers.IO + defaultExceptionHandler(tag, onExceptionOccurred, buildType)
}

fun defaultExceptionHandler(
    tag: String? = null,
    onExceptionOccurred: (Throwable) -> Unit = {},
    buildType: String
): CoroutineExceptionHandler {
    return CoroutineExceptionHandler { _, throwable ->
        log(
            message = "ERROR - ${throwable.message}",
            tag = tag,
            isError = true,
            buildType = buildType
        )
        if (buildType != "debug") throwable.printStackTrace()
        onExceptionOccurred(throwable)
    }
}

fun defaultExecuteHandlingError(
    tag: String? = null,
    onExceptionOccurred: (Throwable) -> Unit = {},
    lambda: suspend CoroutineScope.() -> Unit,
    buildType: String
) {
    CoroutineScope(
        defaultContext(tag, onExceptionOccurred, buildType)
    ).launch {
        lambda()
    }
}