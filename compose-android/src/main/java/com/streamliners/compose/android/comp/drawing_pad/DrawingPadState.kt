package com.streamliners.compose.android.comp.drawing_pad

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Path
import com.streamliners.compose.android.comp.CaptureState
import com.streamliners.compose.android.comp.capture

class DrawingPadState(
    val captureState: MutableState<CaptureState?>,
    val path: MutableState<Path>
) {
    fun isBlank() = path.value.isEmpty
}

@Composable
fun rememberDrawingPadState(): DrawingPadState {
    return remember {
        DrawingPadState(mutableStateOf(null), mutableStateOf(Path()))
    }
}

fun DrawingPadState.capture(callback: (Bitmap) -> Unit) {
    captureState.capture(callback)
}