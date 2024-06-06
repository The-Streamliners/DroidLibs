package com.streamliners.pickers.media

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.mr0xf00.easycrop.AspectRatio

sealed class MediaPickerDialogState {

    data object Hidden: MediaPickerDialogState()

    class ShowMediaPicker(
        val type: MediaType,
        val allowMultiple: Boolean,
        val fromGalleryType: FromGalleryType,
        val cropParams: MediaPickerCropParams = MediaPickerCropParams.Disabled,
        val callback: (suspend () -> List<PickedMedia>) -> Unit
    ): MediaPickerDialogState() {

        fun title(): String {
            return "Attach ${type.name}${if (allowMultiple) "s" else ""}"
        }
    }

    class ShowImageCropper(
        val cropParams: MediaPickerCropParams = MediaPickerCropParams.Disabled
    ): MediaPickerDialogState()
}

sealed class PickedMedia(
    open val uri: String,
    open val filePath: String? = null
) {
    data class Image(
        override val uri: String,
        override val filePath: String? = null
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

sealed class MediaPickerCropParams {

    data object Disabled: MediaPickerCropParams()

    data class Enabled(
        val showAspectRatioSelectionButton: Boolean = true,
        val showShapeCropButton: Boolean = true,
        val lockAspectRatio: AspectRatio? = null
    ): MediaPickerCropParams()

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

fun MutableState<MediaPickerDialogState>.showImageCropper(
    params: MediaPickerCropParams
) {
    value = MediaPickerDialogState.ShowImageCropper(params)
}