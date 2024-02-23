package com.streamliners.feature.compose.sample

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.BottomSheet

@Composable
fun BottomSheetSample(
    bottomSheetState: MutableState<Boolean>
) {
    BottomSheet(
        title = "Bottom Sheet Sample",
        state = bottomSheetState
    ) {
        Text(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 50.dp),
            text = "Bottom Sheet Content goes here...",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}