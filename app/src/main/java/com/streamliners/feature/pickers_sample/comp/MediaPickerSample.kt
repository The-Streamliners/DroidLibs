package com.streamliners.feature.pickers_sample.comp

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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.mr0xf00.easycrop.AspectRatio
import com.mr0xf00.easycrop.rememberImageCropper
import com.streamliners.compose.comp.select.LabelledCheckBox
import com.streamliners.compose.comp.select.RadioGroup
import com.streamliners.pickers.media.FromGalleryType
import com.streamliners.pickers.media.MediaPickerCropParams
import com.streamliners.pickers.media.MediaPickerDialog
import com.streamliners.pickers.media.MediaPickerDialogState
import com.streamliners.pickers.media.MediaType
import com.streamliners.pickers.media.PickedMedia
import com.streamliners.pickers.media.comp.PickedMediaPreviewList
import com.streamliners.pickers.media.rememberMediaPickerDialogState

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

            val cropEnable = remember { mutableStateOf(false) }

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

            LabelledCheckBox(state = cropEnable, label = "Enable Crop")

            val pickedMediaList = remember {
                mutableStateListOf<PickedMedia>()
            }

            val context = LocalContext.current

            Button(
                modifier = Modifier.align(CenterHorizontally),
                enabled = type.value != null && fromGalleryType.value != null,
                onClick = {
                    mediaPickerDialogState.value = MediaPickerDialogState.ShowMediaPicker(
                        type = type.value!!,
                        allowMultiple = allowMultiple.value,
                        fromGalleryType = fromGalleryType.value!!,
                        cropParams = if (cropEnable.value) {
                            MediaPickerCropParams.Enabled(
                                showAspectRatioSelectionButton = false,
                                showShapeCropButton = false,
                                lockAspectRatio = AspectRatio(1, 1)
                            )
                        } else MediaPickerCropParams.Disabled,
                        callback = { getResult ->
                            executeHandlingError {
                                pickedMediaList.addAll(getResult())
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
}