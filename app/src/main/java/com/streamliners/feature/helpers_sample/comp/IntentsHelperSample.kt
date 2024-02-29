package com.streamliners.feature.helpers_sample.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.OpenInBrowser
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Whatsapp
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.config.InputConfig
import com.streamliners.compose.comp.textInput.config.email
import com.streamliners.compose.comp.textInput.config.fixedLengthNumber
import com.streamliners.compose.comp.textInput.config.longText
import com.streamliners.compose.comp.textInput.config.text
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.ifValidInput
import com.streamliners.compose.comp.textInput.state.nullableValue
import com.streamliners.helpers.IntentsHelper
import com.streamliners.helpers.rememberIntentsHelper

@Composable
fun IntentsHelperSample() {

    ElevatedCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = "Intents Helper",
                style = MaterialTheme.typography.titleLarge
            )

            val intentsHelper = rememberIntentsHelper()

            DialAndWhatsAppSample(intentsHelper)
            HorizontalDivider()

            ShareTextSample(intentsHelper)
            HorizontalDivider()

            EmailSample(intentsHelper)
            HorizontalDivider()

            OpenPlayStorePageSample(intentsHelper)
            HorizontalDivider()

            BrowseURLSample(intentsHelper)
            HorizontalDivider()

            OthersSample(intentsHelper)
        }
    }
}

@Composable
fun OthersSample(intentsHelper: IntentsHelper) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Other",
            style = MaterialTheme.typography.titleMedium
        )

        Button(
            modifier = Modifier.align(CenterHorizontally),
            onClick = { intentsHelper.openDateTimeSettings() }
        ) {
            Text(text = "Open Date Time Settings")
        }
    }
}

@Composable
fun BrowseURLSample(intentsHelper: IntentsHelper) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Browse URL",
            style = MaterialTheme.typography.titleMedium
        )

        val url = remember {
            mutableStateOf(
                TextInputState(
                    label = "URL",
                    value = "https://thestreamliners.in/",
                    inputConfig = InputConfig.text()
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
            modifier = Modifier.align(CenterHorizontally),
            onClick = {
                url.ifValidInput { intentsHelper.browseUsingCustomTab(it) }
            }
        ) {
            Text(text = "Browse using Custom Tab")
        }
    }

}

@Composable
fun OpenPlayStorePageSample(intentsHelper: IntentsHelper) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Play Store Page",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.size(4.dp))

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

}

@Composable
fun ShareTextSample(intentsHelper: IntentsHelper) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Text(
            text = "Share Text",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.size(4.dp))

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

}

@Composable
fun EmailSample(intentsHelper: IntentsHelper) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
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

        Text(
            text = "Email",
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.size(4.dp))

        TextInputLayout(state = emailId)
        Spacer(modifier = Modifier.size(8.dp))

        TextInputLayout(state = subject)
        Spacer(modifier = Modifier.size(8.dp))

        TextInputLayout(state = body)
        Spacer(modifier = Modifier.size(8.dp))

        Button(
            modifier = Modifier.align(CenterHorizontally),
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

@Composable
fun DialAndWhatsAppSample(intentsHelper: IntentsHelper) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(8.dp)
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

        Text(
            text = "Dial, WhatsApp Chat",
            style = MaterialTheme.typography.titleMedium
        )

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