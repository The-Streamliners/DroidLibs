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
import androidx.compose.material3.Button
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
fun BrowseURLSample(intentsHelper: IntentsHelper) {

    Collapsible(
        header = {
            Text(
                text = "Browse URL",
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

        val url = remember {
            mutableStateOf(
                TextInputState(
                    label = "URL",
                    value = "https://thestreamliners.in/",
                    inputConfig = InputConfig.text()
                )
            )
        }

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextInputLayout(
                modifier = Modifier.weight(1f),
                state = url
            )

            IconButton(
                onClick = {
                    url.ifValidInput { intentsHelper.browse(it) }
                }
            ) {
                Icon(imageVector = Icons.Default.OpenInBrowser, contentDescription = "Open")
            }
        }

        Spacer(modifier = Modifier.size(12.dp))

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                url.ifValidInput { intentsHelper.browseUsingCustomTab(it) }
            }
        ) {
            Text(text = "Browse using Custom Tab")
        }
    }

}