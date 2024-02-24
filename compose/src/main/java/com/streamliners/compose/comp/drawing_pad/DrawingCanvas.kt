package com.streamliners.compose.comp.drawing_pad

import android.view.MotionEvent
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.RequestDisallowInterceptTouchEvent
import androidx.compose.ui.input.pointer.pointerInteropFilter

@OptIn(ExperimentalComposeUiApi::class)
@Composable
internal fun DrawingCanvas(path: MutableState<Path>, movePath: MutableState<Offset?>) {

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