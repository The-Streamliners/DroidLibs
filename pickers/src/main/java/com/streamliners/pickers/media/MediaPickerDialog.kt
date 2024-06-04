package com.streamliners.pickers.media

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import com.mr0xf00.easycrop.CropError
import com.mr0xf00.easycrop.CropResult
import com.mr0xf00.easycrop.CropperStyle
import com.mr0xf00.easycrop.ImageCropper
import com.mr0xf00.easycrop.crop
import com.mr0xf00.easycrop.rememberImageCropper
import com.mr0xf00.easycrop.ui.ImageCropperDialog
import com.streamliners.pickers.media.FromGalleryType.VisualMediaPicker
import com.streamliners.pickers.media.MediaType.Image
import com.streamliners.pickers.media.MediaType.Video
import com.streamliners.pickers.media.comp.OptionButton
import com.streamliners.pickers.media.util.VideoMetadataExtractor
import com.streamliners.pickers.media.util.createFile
import com.streamliners.pickers.media.util.getUri
import com.streamliners.pickers.media.util.saveBitmapToFile
import com.streamliners.utils.safeLet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.io.File


@Composable
fun MediaPickerDialog(
    state: MutableState<MediaPickerDialogState>,
    authority: String
) {
    val data = state.value as? MediaPickerDialogState.Visible
    val imageCropper = rememberImageCropper()

    imageCropper.cropState?.let {

        ImageCropperDialog(
            state = it,
            style = CropperStyle(
                autoZoom = false,
                guidelines = null
            ),
            showAspectRatioSelectionButton = (data?.cropParams as? MediaPickerCropParams.Enabled)?.showAspectRatioSelectionButton ?: true,
            showShapeCropButton = (data?.cropParams as? MediaPickerCropParams.Enabled)?.showAspectRatioSelectionButton ?: true,
            lockAspectRatio = (data?.cropParams as? MediaPickerCropParams.Enabled)?.lockAspectRatio
        )
    }

    if (data == null) return

    LaunchedEffect(key1 = Unit) {
        if (data.cropParams is MediaPickerCropParams.Enabled) {
            if (data.type == Video) error("cropParams are not allowed for MediaType.Video")
            if (data.allowMultiple) error("cropParams are not allowed when allowMultiple = true")
        }
    }

    val context = LocalContext.current

    val cameraPermissionIsGranted = {
        ContextCompat.checkSelfPermission(
            context, Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }

    AlertDialog(
        modifier = Modifier
            .padding(28.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        properties = DialogProperties(
            usePlatformDefaultWidth = false
        ),
        onDismissRequest = { state.dismiss() },
        title = {
            Text(text = data.title())
        },
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    FromCameraButton(
                        modifier = Modifier.weight(1f),
                        state, data, authority, cameraPermissionIsGranted, imageCropper
                    )

                    FromGalleryButton(
                        modifier = Modifier.weight(1f),
                        state, data, imageCropper, authority
                    )
                }

                if (!cameraPermissionIsGranted()) {
                    Text(text = "Camera permission is required to capture image / record video.")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { state.dismiss() }) {
                Text(text = "CANCEL")
            }
        }
    )
}

@Composable
fun FromCameraButton(
    modifier: Modifier,
    state: MutableState<MediaPickerDialogState>,
    data: MediaPickerDialogState.Visible,
    authority: String,
    cameraPermissionIsGranted: () -> Boolean,
    imageCropper: ImageCropper
) {
    val context = LocalContext.current

    val filePath = remember { mutableStateOf<String?>(null) }
    val fileUri = remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                safeLet(filePath.value, fileUri.value) { path, uri ->

                    when (data.type) {
                        Image -> {
                            showImageCropperIfRequired(
                                data,
                                PickedMedia.Image(uri, path),
                                imageCropper,
                                context,
                                scope,
                                authority
                            ) {
                                data.callback { listOf(it) }
                            }
                        }
                        Video -> {
                            data.callback {
                                listOf(
                                    processVideo(context, uri, path)
                                )
                            }
                        }
                    }
                }
                state.dismiss()
            }
        }
    )

    val launchCamera = {
        val extension = if (data.type == Image) "jpg" else "mp4"
        val fileName = "${System.currentTimeMillis()}.$extension"

        val file = createFile(context, fileName, "capture")
        val uri = file.getUri(context, authority)

        filePath.value = file.path
        fileUri.value = uri.toString()

        val intent = Intent(
            if (data.type == Image)
                MediaStore.ACTION_IMAGE_CAPTURE
            else
                MediaStore.ACTION_VIDEO_CAPTURE
        ).apply {
            putExtra(MediaStore.EXTRA_OUTPUT, uri)

            if (data.type == Video) {
                putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 0.5f)
            }

            addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
        }

        cameraLauncher.launch(intent)
    }

    val cameraPermissionsLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { granted ->
            if (granted) launchCamera()
        }
    )

    OptionButton(
        modifier = modifier,
        icon = if (data.type == Image) Icons.Default.Camera else Icons.Default.Videocam,
        label = if (data.type == Image) "Capture" else "Record"
    ) {
        if (cameraPermissionIsGranted()) {
            launchCamera()
        } else {
            cameraPermissionsLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }
}

@Composable
fun FromGalleryButton(
    modifier: Modifier,
    state: MutableState<MediaPickerDialogState>,
    data: MediaPickerDialogState.Visible,
    imageCropper: ImageCropper,
    authority: String
) {
    val context = LocalContext.current

    val scope = rememberCoroutineScope()

    val documentPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val items = buildList {
                    add(result.data?.data)

                    result.data?.clipData?.run {
                        repeat(itemCount) {
                            add(
                                getItemAt(it).uri
                            )
                        }
                    }
                }.filterNotNull()

                when (data.type) {
                    Image -> {
                        if (data.allowMultiple) {
                            data.callback {
                                items.map { uri ->
                                    PickedMedia.Image(uri.toString())
                                }
                            }
                        } else {
                            showImageCropperIfRequired(
                                data,
                                PickedMedia.Image(items.first().toString()),
                                imageCropper,
                                context,
                                scope,
                                authority
                            ) {
                                data.callback { listOf(it) }
                            }
                        }
                    }
                    Video -> {
                        data.callback {
                            items.map { uri ->
                                processVideo(context, uri.toString())
                            }
                        }
                    }
                }

                state.dismiss()
            }
        }
    )

    val visualMediaSingleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            if (uri != null) {
                context.contentResolver.takePersistableUriPermission(
                    uri,
                    Intent.FLAG_GRANT_READ_URI_PERMISSION
                )

                when (data.type) {
                    Image -> {
                        showImageCropperIfRequired(
                            data,
                            PickedMedia.Image(uri.toString()),
                            imageCropper,
                            context,
                            scope,
                            authority
                        ) {
                            data.callback { listOf(it) }
                        }
                    }
                    Video -> {
                        data.callback {
                            listOf(
                                processVideo(context, uri.toString())
                            )
                        }
                    }
                }
            }
            state.dismiss()
        }
    )

    val visualMediaMultipleLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickMultipleVisualMedia(5) ,
        onResult = { uris ->
            data.callback {
                uris.map { uri ->
                    context.contentResolver.takePersistableUriPermission(
                        uri,
                        Intent.FLAG_GRANT_READ_URI_PERMISSION
                    )

                    when (data.type) {
                        Image -> PickedMedia.Image(uri.toString())
                        Video -> processVideo(context, uri.toString())
                    }
                }
            }
            state.dismiss()
        }
    )

    OptionButton(
        modifier = modifier,
        icon = Icons.Default.Image,
        label = "Pick"
    ) {
        if (data.fromGalleryType == VisualMediaPicker) {
            val request = PickVisualMediaRequest(
                if (data.type == Image)
                    ActivityResultContracts.PickVisualMedia.ImageOnly
                else
                    ActivityResultContracts.PickVisualMedia.VideoOnly
            )

            if (data.allowMultiple) {
                visualMediaMultipleLauncher.launch(request)
            } else {
                visualMediaSingleLauncher.launch(request)
            }
        } else {
            documentPickerLauncher.launch(
                Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
                    type = data.type.mime()
                    putExtra(
                        Intent.EXTRA_ALLOW_MULTIPLE, data.allowMultiple
                    )
                    flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
                }
            )
        }
    }
}

suspend fun processVideo(
    context: Context,
    uri: String,
    path: String? = null
): PickedMedia.Video {
    return PickedMedia.Video(
        uri = uri,
        filePath = path,
        duration = VideoMetadataExtractor.getDuration(context, uri),
        thumbnailUri = VideoMetadataExtractor.getThumbnailUri(context, uri)
    )
}

fun showImageCropperIfRequired(
    data: MediaPickerDialogState.Visible,
    image: PickedMedia.Image,
    imageCropper: ImageCropper,
    context: Context,
    scope: CoroutineScope,
    authority: String,
    onReady: (PickedMedia.Image) -> Unit
) {
    data.cropParams as? MediaPickerCropParams.Enabled ?: run {
        onReady(image)
        return
    }

    scope.launch {

//        val bitmap = MediaStore.Images.Media.getBitmap(context.contentResolver, Uri.parse(image.uri))

        when (
//            val result = imageCropper.crop(bmp = bitmap.asImageBitmap())
            val result = imageCropper.crop(image.uri.toUri(), context, cacheBeforeUse = false)
        ) {
            CropResult.Cancelled -> {
                error("Crop cancelled")
            }
            is CropError -> {
                error("Crop error : ${result.name}")
            }
            is CropResult.Success -> {
                val croppedImageUri = saveBitmapToFile(context, result.bitmap, authority)
                onReady(PickedMedia.Image(croppedImageUri.toString()))
            }
        }
    }
}