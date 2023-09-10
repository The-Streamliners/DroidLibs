package com.streamliners.common.util

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream

fun createFile(
    context: Context,
    fileName: String,
    rootFolder: File = context.externalCacheDir
        ?: error("Unable to access externalCacheDir"),
    folder: String
): File {
    val dir = File(rootFolder, folder)
    if (!dir.exists()) dir.mkdir()

    val file = File(dir, fileName)
    if (!file.exists()) file.createNewFile()

    return file
}

fun File.writeBitmap(bitmap: Bitmap) {
    val outputStream = FileOutputStream(this)
    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
    outputStream.close()
}

fun Context.viewMediaFile(fileUri: Uri, mime: String) {
    Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(fileUri, mime)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(this)
    }
}

fun File.getUri(context: Context, authority: String): Uri {
    return FileProvider.getUriForFile(context, authority, this)
}