package com.streamliners.compose.android.comp

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.drawToBitmap

class CaptureState(
    val callback: (Bitmap) -> Unit
)

@Composable
fun rememberCaptureState(): MutableState<CaptureState?> {
    return remember { mutableStateOf(null) }
}

fun MutableState<CaptureState?>.capture(callback: (Bitmap) -> Unit) {
    value = CaptureState(callback)
}

@Composable
fun Capturable(
    state: MutableState<CaptureState?>,
    content: @Composable () -> Unit
) {

    val context = LocalContext.current

    val composeView = remember { ComposeView(context) }

    LaunchedEffect(state.value) {
        state.value?.let {
            composeView.post {
                it.callback(
                    composeView.drawToBitmap()
                )
            }
        }
    }

    AndroidView(
        factory = {
            composeView.apply {
                setContent {
                    content.invoke()
                }
            }
        }
    )
}