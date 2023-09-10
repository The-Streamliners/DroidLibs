package com.streamliners.compose.comp

import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun FilledIconButtonSmall(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    color: Color,
    onClick: () -> Unit = { },
    enabled: Boolean = true
) {

    FilledIconButton(
        modifier = modifier.size(24.dp),
        onClick = onClick,
        colors = IconButtonDefaults.filledIconButtonColors(containerColor = color),
        enabled = enabled
    ) {
        Icon(modifier = Modifier.size(16.dp), imageVector = icon, contentDescription = "", tint = Color.White)
    }
}