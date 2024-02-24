package com.streamliners.feature.compose.drawing_pad

import android.content.Intent
import android.graphics.Bitmap
import androidx.core.content.FileProvider
import com.streamliners.base.BaseActivity
import com.streamliners.base.ext.showToast
import java.io.File
import java.io.FileOutputStream

fun BaseActivity.saveAndShareImage(bitmap: Bitmap) {
    // Save file
    val file = File(getExternalFilesDir(null), "capture.png")
    val outputStream = FileOutputStream(file)
    bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)

    // Get file URI
    val fileUri = FileProvider.getUriForFile(
        this,
        "com.streamliners.fileprovider",
        file
    )

    // Create an intent to view the file
    val viewIntent = Intent(Intent.ACTION_SEND).apply {
        type = "image/png"
        flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        putExtra(
            Intent.EXTRA_STREAM,
            fileUri
        )
    }

    if (viewIntent.resolveActivity(packageManager) != null) {
        startActivity(viewIntent)
    } else {
        showToast("No application found to share the file")
    }
}