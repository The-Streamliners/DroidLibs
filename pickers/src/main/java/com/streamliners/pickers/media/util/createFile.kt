package com.streamliners.pickers.media.util

import android.content.Context
import java.io.File

fun createFile(context: Context, fileName: String, folder: String): File {
    val dir = File(context.externalCacheDir, folder)
    if (!dir.exists()) dir.mkdir()

    val file = File(dir, fileName)
    if (!file.exists()) file.createNewFile()

    return file
}