package com.streamliners.pickers.media

import androidx.compose.runtime.MutableState

sealed class MediaPickerDialogState {

    data object Hidden: MediaPickerDialogState()

    class Visible(
        val type: MediaType,
        val allowMultiple: Boolean,
        val callback: (suspend () -> List<PickedMedia>) -> Unit
    ): MediaPickerDialogState() {

        fun title(): String {
            return "Attach ${type.name}${if (allowMultiple) "s" else ""}"
        }
    }
}

sealed class PickedMedia(
    val uri: String,
    val filePath: String? = null
) {
    class Image(
        uri: String,
        filePath: String? = null
    ): PickedMedia(uri, filePath)

    class Video(
        uri: String,
        filePath: String? = null,
        val duration: String,
        val thumbnail: String
    ): PickedMedia(uri, filePath)
}

enum class MediaType {
    Image, Video;

    fun mime(): String {
        return when (this) {
            Image -> "image/*"
            Video -> "video/*"
        }
    }
}

fun MutableState<MediaPickerDialogState>.dismiss() {
    value = MediaPickerDialogState.Hidden
}