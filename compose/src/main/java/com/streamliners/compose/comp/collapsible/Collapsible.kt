package com.streamliners.compose.comp.collapsible

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Collapsible(
    header: @Composable () -> Unit,
    content: @Composable () -> Unit,
    initialState: Boolean = true
) {
    var collapsed by remember { mutableStateOf(initialState) }

    val rotation = animateFloatAsState(
        targetValue = if (collapsed) 0f else 90f,
        label = "Rotation"
    )

    Column {
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .clickable { collapsed = !collapsed }
                .padding(end = 8.dp)
                .padding(vertical = 4.dp)
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .rotate(rotation.value),
                imageVector = Icons.Filled.KeyboardArrowRight,
                contentDescription = "Expand"
            )

            header()
        }

        AnimatedVisibility(
            visible = !collapsed
        ) {
            Box(
                Modifier.padding(start = 34.dp, bottom = 12.dp)
            ) {
                content()
            }
        }
    }
}