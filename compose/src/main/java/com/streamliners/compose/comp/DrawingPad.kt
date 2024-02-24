package com.streamliners.compose.comp

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.RequestDisallowInterceptTouchEvent
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.unit.dp

@Composable
fun DrawingPad(
    modifier: Modifier,
    captureState: MutableState<CaptureState?>,
    path: MutableState<Path>
) {

    Box(
        modifier = modifier
    ) {
        val movePath = remember { mutableStateOf<Offset?>(null) }

        Capturable(captureState.value) {
            DrawingCanvas(path, movePath)
        }

        FilledIconButton(
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(12.dp),
            onClick = {
                path.value = Path()
                movePath.value = null
            }
        ) {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "Clear"
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun DrawingCanvas(path: MutableState<Path>, movePath: MutableState<Offset?>) {

    val requestDisallowIntercept = remember {
        RequestDisallowInterceptTouchEvent()
    }

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .pointerInteropFilter(requestDisallowIntercept) {

                requestDisallowIntercept(true)

                when (it.action) {
                    MotionEvent.ACTION_DOWN -> {
                        path.value.moveTo(it.x, it.y)
                    }

                    MotionEvent.ACTION_MOVE -> {
                        movePath.value = Offset(it.x, it.y)
                    }

                    else -> {
                        movePath.value = null
                    }
                }
                true
            }

    ) {
        movePath.value?.let {
            path.value.lineTo(it.x,it.y)
            drawPath(
                path = path.value,
                color = Color.Black,
                style = Stroke(5f)
            )
        }

        drawPath(
            path = path.value,
            color = Color.Black,
            style = Stroke(5f)
        )
    }
}