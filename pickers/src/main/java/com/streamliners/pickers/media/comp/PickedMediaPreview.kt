package com.streamliners.pickers.media.comp

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Videocam
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.AsyncImage
import com.streamliners.compose.comp.FilledIconButtonSmall
import com.streamliners.pickers.media.PickedMedia
import com.streamliners.pickers.media.util.viewMediaFile

@Composable
fun PickedMediaPreview(
    media: PickedMedia,
    remove: () -> Unit
) {
    val context = LocalContext.current

    val size = if (media is PickedMedia.Image)
        DpSize(80.dp, 80.dp)
    else
        DpSize(100.dp, 80.dp)

    val view: () -> Unit = {
        context.viewMediaFile(
            Uri.parse(media.uri), media.mime()
        )
    }

    Box {

        AsyncImage(
            modifier = Modifier
                .padding(8.dp)
                .size(size)
                .clip(RoundedCornerShape(8.dp)),
            data = media.thumbnailUri(),
            onClick = view,
            contentScale = ContentScale.Crop
        )

        FilledIconButtonSmall(
            modifier = Modifier.align(Alignment.TopEnd),
            icon = Icons.Default.Close,
            colors = IconButtonDefaults.iconButtonColors(
                containerColor = Color(0xFFF16F25),
                contentColor = Color.White
            ),
            onClick = remove
        )

        if (media is PickedMedia.Video) {
            Icon(
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.scrim)
                    .padding(horizontal = 4.dp)
                    .size(20.dp),
                imageVector = Icons.Default.Videocam,
                contentDescription = "Video",
                tint = Color.White
            )

            Text(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(12.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(MaterialTheme.colorScheme.scrim)
                    .padding(horizontal = 4.dp),
                text = media.duration,
                style = MaterialTheme.typography.labelSmall.copy(
                    shadow = Shadow(blurRadius = 2f)
                ),
                color = Color.White
            )
        }
    }
}