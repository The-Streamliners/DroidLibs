package com.streamliners.pickers.media

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import androidx.core.content.ContextCompat
import com.streamliners.pickers.media.FromGalleryType.VisualMediaPicker
import com.streamliners.pickers.media.MediaType.Image
import com.streamliners.pickers.media.MediaType.Video
import com.streamliners.pickers.media.comp.OptionButton
import com.streamliners.pickers.media.util.VideoMetadataExtractor
import com.streamliners.pickers.media.util.createFile
import com.streamliners.pickers.media.util.getUri
import com.streamliners.utils.safeLet

@Composable
fun MediaPickerDialog(
    state: MutableState<MediaPickerDialogState>,
    authority: String
) {
    val data = state.value as? MediaPickerDialogState.Visible ?: return

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
                        state, data, authority, cameraPermissionIsGranted
                    )

                    FromGalleryButton(
                        modifier = Modifier.weight(1f),
                        state, data
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
    cameraPermissionIsGranted: () -> Boolean
) {
    val context = LocalContext.current

    val filePath = remember { mutableStateOf<String?>(null) }
    val fileUri = remember { mutableStateOf<String?>(null) }

    val cameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult(),
        onResult = { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                safeLet(filePath.value, fileUri.value) { path, uri ->
                    data.callback {
                        listOf(
                            when (data.type) {
                                Image -> PickedMedia.Image(uri, path)
                                Video -> processVideo(context, uri, path)
                            }
                        )
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
    data: MediaPickerDialogState.Visible
) {
    val context = LocalContext.current

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

                data.callback {
                    items.map { uri ->
                        when (data.type) {
                            Image -> PickedMedia.Image(uri.toString())
                            Video -> processVideo(context, uri.toString())
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
                data.callback {
                    listOf(
                        when (data.type) {
                            Image -> PickedMedia.Image(uri.toString())
                            Video -> processVideo(context, uri.toString())
                        }
                    )
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