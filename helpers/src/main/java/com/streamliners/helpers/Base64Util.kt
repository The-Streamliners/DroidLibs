package com.streamliners.helpers

import android.util.Base64

fun String.encodeToBase64(): String {
    return String(
        Base64.encode(toByteArray(), 0)
    )
}

fun String.decodeFromBase64(): String {
    return String(
        Base64.decode(this, 0)
    )
}