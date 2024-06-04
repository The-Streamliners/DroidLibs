package com.streamliners.pickers.media.util

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import java.io.FileOutputStream

fun saveBitmapToFile(context: Context, bitmap: ImageBitmap, authority: String): Uri {
    // TODO : get extension from previous file instead of hardcoding as png
    val file = createFile(context, "${System.currentTimeMillis()}.png", "capture")
    val fileOutputStream = FileOutputStream(file)
    bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
    fileOutputStream.flush()
    return file.getUri(context, authority)
}