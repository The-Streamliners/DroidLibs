package com.streamliners.compose.comp

import androidx.compose.foundation.layout.size
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun FilledIconButtonSmall(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    colors: IconButtonColors = IconButtonDefaults.filledIconButtonColors(),
    contentDescription: String? = null,
    onClick: () -> Unit = { },
    enabled: Boolean = true
) {

    FilledIconButton(
        modifier = modifier.size(24.dp),
        onClick = onClick,
        colors = colors,
        enabled = enabled
    ) {
        Icon(
            modifier = Modifier.size(16.dp),
            imageVector = icon,
            contentDescription = contentDescription ?: ""
        )
    }
}