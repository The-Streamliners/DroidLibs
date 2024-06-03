package com.streamliners.feature.pickers_sample.comp

import android.content.Context
import android.graphics.Bitmap
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.mr0xf00.easycrop.AspectRatio
import com.mr0xf00.easycrop.CropError
import com.mr0xf00.easycrop.CropResult
import com.mr0xf00.easycrop.CropperStyle
import com.mr0xf00.easycrop.crop
import com.mr0xf00.easycrop.rememberImageCropper
import com.mr0xf00.easycrop.ui.ImageCropperDialog
import com.streamliners.compose.comp.select.LabelledCheckBox
import com.streamliners.compose.comp.select.RadioGroup
import com.streamliners.pickers.media.FromGalleryType
import com.streamliners.pickers.media.MediaPickerDialog
import com.streamliners.pickers.media.MediaPickerDialogState
import com.streamliners.pickers.media.MediaType
import com.streamliners.pickers.media.PickedMedia
import com.streamliners.pickers.media.comp.PickedMediaPreviewList
import com.streamliners.pickers.media.rememberMediaPickerDialogState
import com.streamliners.pickers.media.util.createFile
import com.streamliners.pickers.media.util.getUri
import java.io.FileOutputStream

@Composable
fun MediaPickerSample(
    executeHandlingError: (suspend () -> Unit) -> Unit
) {

    val mediaPickerDialogState = rememberMediaPickerDialogState()

    val imageCropper = rememberImageCropper()

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Media Picker",
                style = MaterialTheme.typography.titleLarge
            )

            val type = remember {
                mutableStateOf<MediaType?>(null)
            }

            val fromGalleryType = remember {
                mutableStateOf<FromGalleryType?>(null)
            }

            val allowMultiple = remember { mutableStateOf(false) }

            RadioGroup(
                state = type,
                options = MediaType.entries.toList(),
                labelExtractor = { it.name }
            )

            HorizontalDivider()

            RadioGroup(
                state = fromGalleryType,
                options = FromGalleryType.entries.toList(),
                labelExtractor = { it.name }
            )

            HorizontalDivider()

            LabelledCheckBox(state = allowMultiple, label = "Allow multiple")

            val pickedMediaList = remember {
                mutableStateListOf<PickedMedia>()
            }

            val context = LocalContext.current

            Button(
                modifier = Modifier.align(CenterHorizontally),
                enabled = type.value != null && fromGalleryType.value != null,
                onClick = {
                    mediaPickerDialogState.value = MediaPickerDialogState.Visible(
                        type = type.value!!,
                        allowMultiple = allowMultiple.value,
                        fromGalleryType = fromGalleryType.value!!,
                        callback = { getResult ->
                            executeHandlingError {
                                pickedMediaList.addAll(getResult())

                                val firstMedia = pickedMediaList.firstOrNull() ?: error("no media picked")
                                if (firstMedia is PickedMedia.Image) {

                                    val result = imageCropper.crop(firstMedia.uri.toUri(), context)
                                    when (result) {
                                        CropResult.Cancelled -> {

                                        }
                                        is CropError -> {
                                            error("Crop error")
                                        }
                                        is CropResult.Success -> {
                                            val croppedImageUri = saveBitmapToFile(context, result.bitmap)
                                            pickedMediaList.remove(firstMedia)
                                            pickedMediaList.add(firstMedia.copy(uri = croppedImageUri.toString()))
                                        }
                                    }
                                }
                            }
                        }
                    )
                }
            ) {
                Text(text = "Launch")
            }

            if (pickedMediaList.isNotEmpty()) {
                PickedMediaPreviewList(
                    list = pickedMediaList,
                    remove = { pickedMediaList.remove(it) }
                )
            }
        }
    }

    MediaPickerDialog(
        state = mediaPickerDialogState,
        authority = "com.streamliners.fileprovider"
    )

    imageCropper.cropState?.let {

        ImageCropperDialog(
            state = it,
            style = CropperStyle(
                autoZoom = false,
                guidelines = null
            ),
            showAspectRatioSelectionButton = false,
            showShapeCropButton = false,
            lockAspectRatio = AspectRatio(1, 1)
        )
    }
}

fun saveBitmapToFile(context: Context, bitmap: ImageBitmap): Uri {
    val file = createFile(context, "${System.currentTimeMillis()}.png", "capture")
    val fileOutputStream = FileOutputStream(file)
    bitmap.asAndroidBitmap().compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)
    fileOutputStream.flush()
    return file.getUri(context, "com.streamliners.fileprovider")
}

