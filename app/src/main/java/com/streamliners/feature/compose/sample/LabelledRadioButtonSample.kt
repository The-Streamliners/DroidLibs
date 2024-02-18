package com.streamliners.feature.compose.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.select.LabelledRadioButton

@Composable
fun LabelledRadioButtonSample() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            val gender = remember { mutableStateOf<String?>(null) }

            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = "Labelled Radio Button",
                style = MaterialTheme.typography.titleLarge
            )

            listOf("Male", "Female", "Other").forEach { option ->
                LabelledRadioButton(
                    label = option,
                    selected = gender.value == option,
                    onClick = { gender.value = option }
                )
            }
        }
    }
}