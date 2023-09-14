package com.streamliners.base.exception

import com.streamliners.base.exception.BusinessException.Level
import com.streamliners.base.exception.BusinessException.Level.MEDIUM

class BusinessException(
    override val message: String,
    val level: Level = MEDIUM
): Exception(message) {

    enum class Level {
        LOW,    // Toast is shown
        MEDIUM, // Cancellable dialog is shown
        HIGH    // Non-Cancellable dialog is shown
    }
}

fun failure(
    message: String,
    level: Level = MEDIUM
): Nothing {
    throw BusinessException(message, level)
}

class OfflineException: Exception()
class LoggedOutException: Exception()
class ServerException(override val message: String?): Exception()