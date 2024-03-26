package com.streamliners.utils

inline fun <T1, T2, R> safeLet(
    value1: T1?,
    value2: T2?,
    block: (T1, T2) -> R
): R? {
    return if (value1 != null && value2 != null) {
        block(value1, value2)
    } else {
        null
    }
}

inline fun <T1, T2, T3, R> safeLet(
    value1: T1?,
    value2: T2?,
    value3: T3?,
    block: (T1, T2, T3) -> R
): R? {
    return if (value1 != null && value2 != null && value3 != null) {
        block(value1, value2, value3)
    } else {
        null
    }
}

inline fun <T1, T2, T3, T4, R> safeLet(
    value1: T1?,
    value2: T2?,
    value3: T3?,
    value4: T4?,
    block: (T1, T2, T3, T4) -> R
): R? {
    return if (value1 != null && value2 != null && value3 != null && value4 != null) {
        block(value1, value2, value3, value4)
    } else {
        null
    }
}

inline fun <T1, T2, T3, T4, T5, R> safeLet(
    value1: T1?,
    value2: T2?,
    value3: T3?,
    value4: T4?,
    value5: T5?,
    block: (T1, T2, T3, T4, T5) -> R
): R? {
    return if (value1 != null && value2 != null && value3 != null && value4 != null && value5 != null) {
        block(value1, value2, value3, value4, value5)
    } else {
        null
    }
}