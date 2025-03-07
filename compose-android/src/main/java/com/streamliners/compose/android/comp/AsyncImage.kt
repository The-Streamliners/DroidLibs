package com.streamliners.compose.android.comp

import android.graphics.drawable.Drawable
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun AsyncImage(
    modifier: Modifier,
    data: String,
    contentScale: ContentScale = ContentScale.Fit,
    circleCrop: Boolean = false,
    placeHolderResId: Int? = null,
    placeHolderDrawable: Drawable? = null,
    onClick: (() -> Unit)? = null
) {
    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
            .data(data)
            .crossfade(true)
            .run {
                if (placeHolderResId != null) {
                    placeholder(placeHolderResId)
                } else if (placeHolderDrawable != null) {
                    placeholder(placeHolderDrawable)
                } else {
                    this
                }
            }
            .build(),
        contentDescription = "",
        contentScale = contentScale,
        modifier = modifier.run {

            val updatedModifier = if (onClick != null) {
                clickable { onClick() }
            } else this

            if (circleCrop) {
                updatedModifier.clip(CircleShape)
            } else updatedModifier
        }
    )
}