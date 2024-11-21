package com.streamliners.helpers

import com.google.gson.Gson

fun encodeArg(arg: Any): String {
    return Gson().toJson(arg).encodeToBase64()
}

inline fun <reified T> decodeArg(arg: String): T {
    return Gson().fromJson(
        arg.decodeFromBase64(),
        T::class.java
    )
}