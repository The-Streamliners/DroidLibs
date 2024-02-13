package com.streamliners.feature.task_state_sample.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.streamliners.base.taskState.TaskState
import com.streamliners.base.taskState.comp.whenLoaded
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.state.TextInputState

@Composable
fun FactFetcherComp(
    number: MutableState<TextInputState>,
    fetchFactTask: MutableState<TaskState<String>>,
    fetchFact: () -> Unit
) {
    ElevatedCard {

        Column(
            Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Fact Fetcher",
                style = MaterialTheme.typography.titleLarge
            )

            Text(
                text = "using Full Screen blocking loader",
                style = MaterialTheme.typography.bodySmall,
                fontStyle = FontStyle.Italic
            )

            // Number Input and Fetch button
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                // Number Input
                TextInputLayout(
                    modifier = Modifier.weight(1f),
                    state = number
                )

                // Fetch button
                Button(onClick = fetchFact) {
                    Text(text = "Fetch")
                }
            }

            // Fact
            fetchFactTask.whenLoaded {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.labelLarge,
                    text = it
                )
            }
        }
    }
}