package com.streamliners.compose.comp.drawing_pad

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.Capturable

@Composable
fun DrawingPad(
    modifier: Modifier,
    state: DrawingPadState,

    showClearButton: Boolean = true,
    clearButtonIcon: ImageVector = Icons.Default.Refresh,
    clearButtonPadding: Dp = 12.dp,
    clearButtonAlignment: Alignment = Alignment.TopEnd
) {

    Box(
        modifier = modifier
    ) {
        val movePath = remember { mutableStateOf<Offset?>(null) }

        Capturable(state.captureState) {
            DrawingCanvas(state.path, movePath)
        }

        if (showClearButton) {
            FilledIconButton(
                modifier = Modifier
                    .align(clearButtonAlignment)
                    .padding(clearButtonPadding),
                onClick = {
                    state.path.value = Path()
                    movePath.value = null
                }
            ) {
                Icon(
                    imageVector = clearButtonIcon,
                    contentDescription = "Clear"
                )
            }
        }
    }
}