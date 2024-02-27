package com.streamliners.pickers.media.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.pickers.media.PickedMedia

@Composable
fun PickedMediaPreviewList(
    list: List<PickedMedia>,
    remove: (PickedMedia) -> Unit
) {
    LazyRow(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(list) { media ->
            PickedMediaPreview(
                media = media,
                remove = { remove(media) }
            )
        }
    }
}