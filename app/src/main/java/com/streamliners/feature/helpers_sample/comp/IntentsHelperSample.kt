package com.streamliners.feature.helpers_sample.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.feature.helpers_sample.comp.intents_helper.BrowseURLSample
import com.streamliners.feature.helpers_sample.comp.intents_helper.DialAndWhatsAppSample
import com.streamliners.feature.helpers_sample.comp.intents_helper.EmailSample
import com.streamliners.feature.helpers_sample.comp.intents_helper.OpenPlayStorePageSample
import com.streamliners.feature.helpers_sample.comp.intents_helper.OthersSample
import com.streamliners.feature.helpers_sample.comp.intents_helper.ShareTextSample
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