package com.streamliners.feature.helpers_sample.comp.intents_helper

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Whatsapp
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
import com.streamliners.compose.comp.textInput.config.fixedLengthNumber
import com.streamliners.compose.comp.textInput.config.text
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.ifValidInput
import com.streamliners.compose.comp.textInput.state.nullableValue
import com.streamliners.helpers.IntentsHelper

@Composable
fun DialAndWhatsAppSample(intentsHelper: IntentsHelper) {

    Collapsible(
        header = {
            Text(
                text = "Dial, WhatsApp Chat",
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
        val contactNo = remember {
            mutableStateOf(
                TextInputState(
                    label = "Contact No",
                    inputConfig = InputConfig.fixedLengthNumber(10)
                )
            )
        }

        val message = remember {
            mutableStateOf(
                TextInputState(
                    label = "Message",
                    inputConfig = InputConfig.text { optional = true }
                )
            )
        }

        Spacer(modifier = Modifier.size(4.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {


            TextInputLayout(
                modifier = Modifier.weight(1f),
                state = contactNo
            )

            IconButton(
                onClick = {
                    contactNo.ifValidInput { intentsHelper.dial(it) }
                }
            ) {
                Icon(imageVector = Icons.Default.Call, contentDescription = "Dial")
            }
        }

        Spacer(modifier = Modifier.size(12.dp))

        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            TextInputLayout(
                modifier = Modifier.weight(1f),
                state = message
            )

            IconButton(
                onClick = {
                    contactNo.ifValidInput { input ->
                        intentsHelper.whatsAppChat(input, message.nullableValue())
                    }
                }
            ) {
                Icon(imageVector = Icons.Default.Whatsapp, contentDescription = "Chat")
            }
        }
    }
}