package com.streamliners.pickers.media.util

import android.content.Context
import android.content.Intent
import android.net.Uri

fun Context.viewMediaFile(fileUri: Uri, mime: String) {
    Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(fileUri, mime)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        startActivity(this)
    }
}