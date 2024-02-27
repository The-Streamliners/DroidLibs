package com.streamliners.pickers.media

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

sealed class MediaPickerDialogState {

    data object Hidden: MediaPickerDialogState()

    class Visible(
        val type: MediaType,
        val allowMultiple: Boolean,
        val fromGalleryType: FromGalleryType,
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
        val thumbnailUri: String
    ): PickedMedia(uri, filePath)

    fun thumbnailUri(): String {
        return when (this) {
            is Image -> uri
            is Video -> thumbnailUri
        }
    }

    fun mime(): String {
        return when (this) {
            is Image -> "image/*"
            is Video -> "video/*"
        }
    }
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

enum class FromGalleryType {
    DocumentPicker, VisualMediaPicker
}

@Composable
fun rememberMediaPickerDialogState(): MutableState<MediaPickerDialogState> {
    return remember {
        mutableStateOf(MediaPickerDialogState.Hidden)
    }
}

fun MutableState<MediaPickerDialogState>.dismiss() {
    value = MediaPickerDialogState.Hidden
}