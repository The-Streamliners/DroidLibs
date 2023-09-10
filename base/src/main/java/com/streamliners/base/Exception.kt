package com.streamliners.base

import android.util.Log
import com.streamliners.base.BusinessExceptionLevel.MEDIUM
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BusinessException(
    override val message: String,
    val level: BusinessExceptionLevel = MEDIUM
): Exception(message)

enum class BusinessExceptionLevel {
    LOW,    // Toast is shown
    MEDIUM, // Cancellable dialog is shown
    HIGH    // Non-Cancellable dialog is shown
}

class LoggedOutException: Exception()

fun failure(message: String, level: BusinessExceptionLevel = MEDIUM): Nothing {
    throw BusinessException(message, level)
}

fun debugLog(message: String, tag: String = "") {
    Log.i("MyLog", "$tag -> $message")
}

fun defaultScope(tag: String) = Dispatchers.IO + defaultExceptionHandler(tag)

fun defaultExceptionHandler(tag: String) = CoroutineExceptionHandler { _, throwable ->
    debugLog(tag, "ERROR - ${throwable.message}")
    throwable.printStackTrace()
    recordException(throwable)
}

fun defaultExecuteHandlingError(tag: String = "MyLog", lambda: suspend CoroutineScope.() -> Unit) {
    CoroutineScope(defaultScope(tag)).launch {
        lambda()
    }
}

fun recordException(throwable: Throwable) {
//    Firebase.crashlytics.recordException(throwable)
}

class OfflineException: Exception()
class ServerException(override val message: String?): Exception()