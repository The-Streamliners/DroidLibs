package com.streamliners.feature.helpers_sample.comp.intents_helper

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
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
import com.streamliners.compose.comp.textInput.config.email
import com.streamliners.compose.comp.textInput.config.longText
import com.streamliners.compose.comp.textInput.config.text
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.ifValidInput
import com.streamliners.compose.comp.textInput.state.nullableValue
import com.streamliners.helpers.IntentsHelper

@Composable
fun EmailSample(intentsHelper: IntentsHelper) {

    Collapsible(
        header = {
            Text(
                text = "Email",
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
        val emailId = remember {
            mutableStateOf(
                TextInputState("Email id", inputConfig = InputConfig.email())
            )
        }

        val subject = remember {
            mutableStateOf(
                TextInputState(
                    label = "Subject",
                    inputConfig = InputConfig.text { optional = true }
                )
            )
        }

        val body = remember {
            mutableStateOf(
                TextInputState(
                    label = "Body",
                    inputConfig = InputConfig.longText { optional = true }
                )
            )
        }

        TextInputLayout(state = emailId)
        Spacer(modifier = Modifier.size(8.dp))

        TextInputLayout(state = subject)
        Spacer(modifier = Modifier.size(8.dp))

        TextInputLayout(state = body)
        Spacer(modifier = Modifier.size(8.dp))

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                emailId.ifValidInput { input ->
                    intentsHelper.email(
                        input, subject.nullableValue(), body.nullableValue()
                    )
                }
            }
        ) {
            Text(text = "Email")
        }
    }
}