package com.streamliners.feature.compose.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.base.BaseActivity
import com.streamliners.compose.comp.Capturable
import com.streamliners.compose.comp.capture
import com.streamliners.compose.comp.rememberCaptureState
import com.streamliners.data.CountryRepository.Country
import com.streamliners.feature.compose.saveAndShareImage
import com.streamliners.feature.compose.search_bar.CountryCard

@Composable
fun BaseActivity.CapturableSample() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Capturable",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "Captures a composable as Bitmap",
                style = MaterialTheme.typography.bodyLarge
            )

            val captureState = rememberCaptureState()

            Capturable(state = captureState) {
                CountryCard(
                    country = Country("IN", "Bharat", "Asia")
                )
            }

            Button(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                onClick = {
                    captureState.capture { bitmap ->
                        saveAndShareImage(bitmap)
                    }
                }
            ) {
                Text(text = "Capture & Share")
            }
        }
    }
}