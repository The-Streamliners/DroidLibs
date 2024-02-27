package com.streamliners.pickers.media.util

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Build
import android.provider.MediaStore

object VideoMetadataExtractor {

    fun getThumbnailUri(
        context: Context,
        uriString: String
    ): String {
        return try {
            val uri = Uri.parse(uriString)

            val bitmap =  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                val retriever = MediaMetadataRetriever()
                retriever.setDataSource(context, Uri.parse(uriString))
                retriever.getFrameAtIndex(1)
            } else {
                MediaStore.Video.Thumbnails.getThumbnail(
                    context.contentResolver,
                    ContentUris.parseId(uri),
                    MediaStore.Video.Thumbnails.MICRO_KIND,
                    null
                )
            } ?: error("Unable to create thumbnail!")

            val file = createFile(context, "${System.currentTimeMillis()}-thumbnail.jpg", "capture")
            val outputStream = file.outputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()

            file.path
        } catch (_: Exception) {
            "https://www.schemecolor.com/wallpaper?i=24925&desktop"
        }
    }

    fun getDuration(
        context: Context,
        uriString: String
    ): String {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(context, Uri.parse(uriString))
        val time = retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION)
            ?: error("Unable to retrieve video info")
        val duration = time.toLong()
        retriever.release()
        return formattedDuration(duration)
    }

    private fun formattedDuration(duration: Long): String {
        var seconds = (duration / 1000).toInt()

        var minutes = seconds / 60
        seconds -= minutes * 60

        val hours = minutes / 60
        minutes -= hours * 60

        return buildString {
            if (hours > 0) {
                append(
                    hours.toString().padStart(2, '0')
                )

                append(":")
            }

            append(
                minutes.toString().padStart(2, '0')
            )

            append(":")

            append(
                seconds.toString().padStart(2, '0')
            )
        }
    }

}