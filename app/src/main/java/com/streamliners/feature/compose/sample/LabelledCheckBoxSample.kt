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
import com.streamliners.compose.comp.select.LabelledCheckBox

@Composable
fun LabelledCheckBoxSample() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Labelled CheckBox",
                style = MaterialTheme.typography.titleLarge
            )

            val checked = remember { mutableStateOf(false) }

            // MutableState passed directly
            LabelledCheckBox(
                state = checked,
                label = "Gift wrap"
            )

            // Otherwise
            LabelledCheckBox(
                checked = checked.value,
                onToggle = { checked.value = it },
                label = "Gift wrap"
            )
        }
    }
}