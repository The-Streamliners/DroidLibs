package com.streamliners.utils

inline fun <T1, T2, R> safeLet(value1: T1?, value2: T2?, block: (T1, T2) -> R): R? {
    return if (value1 != null && value2 != null) {
        block(value1, value2)
    } else {
        null
    }
}