package com.streamliners.feature.helpers_sample.comp.intents_helper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.collapsible.Collapsible
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.config.InputConfig
import com.streamliners.compose.comp.textInput.config.text
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.ifValidInput
import com.streamliners.helpers.IntentsHelper

@Composable
fun OpenPlayStorePageSample(intentsHelper: IntentsHelper) {

    Collapsible(
        header = {
            Text(
                text = "Play Store Page",
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
    Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        val packageName = remember {
            mutableStateOf(
                TextInputState(
                    label = "Text",
                    value = "com.llamalab.automate",
                    inputConfig = InputConfig.text()
                )
            )
        }

        TextInputLayout(
            modifier = Modifier.weight(1f),
            state = packageName
        )

        IconButton(
            onClick = {
                packageName.ifValidInput { intentsHelper.openPlayStorePage(it) }
            }
        ) {
            Icon(imageVector = Icons.Default.OpenInBrowser, contentDescription = "Open")
        }
    }
}