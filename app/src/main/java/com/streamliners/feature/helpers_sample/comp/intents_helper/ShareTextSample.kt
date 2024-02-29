package com.streamliners.feature.helpers_sample.comp.intents_helper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
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
fun ShareTextSample(intentsHelper: IntentsHelper) {

    Collapsible(
        header = {
            Text(
                text = "Share Text",
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
        val text = remember {
            mutableStateOf(
                TextInputState(
                    label = "Text",
                    inputConfig = InputConfig.text()
                )
            )
        }

        TextInputLayout(
            modifier = Modifier.weight(1f),
            state = text
        )

        IconButton(
            onClick = {
                text.ifValidInput { intentsHelper.shareText(it) }
            }
        ) {
            Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
        }
    }
}