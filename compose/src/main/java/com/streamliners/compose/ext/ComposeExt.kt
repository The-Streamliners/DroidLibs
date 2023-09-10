package com.streamliners.compose.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed

fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier = composed {
    clickable(indication = null,
        interactionSource = remember { MutableInteractionSource() }) {
        onClick()
    }
}

/* A work-around to show enabled colors on disabled OutlinedTextField */
@ExperimentalMaterial3Api
@Composable
fun outlinedTextFieldNormalColors(): TextFieldColors {
    return TextFieldDefaults.outlinedTextFieldColors(
        disabledTextColor = LocalContentColor.current.copy(LocalContentAlpha.current),
        disabledBorderColor =  MaterialTheme.colorScheme.onSurface.copy(alpha = ContentAlpha.disabled),
        disabledLabelColor = MaterialTheme.colorScheme.onSurface.copy(ContentAlpha.medium)
    )
}