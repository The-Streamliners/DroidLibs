package com.streamliners.feature.helpers_sample.comp.intents_helper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.collapsible.Collapsible
import com.streamliners.helpers.IntentsHelper

@Composable
fun OthersSample(intentsHelper: IntentsHelper) {

    Collapsible(
        header = {
            Text(
                text = "Other",
                style = MaterialTheme.typography.titleMedium
            )
        },
        content = {
            Content(intentsHelper)
        }
    )
}

@Composable
private fun Content(intentsHelper: IntentsHelper) {
    Column(
        Modifier.fillMaxWidth()
    ) {
        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = { intentsHelper.openDateTimeSettings() }
        ) {
            Text(text = "Open Date Time Settings")
        }
    }
}