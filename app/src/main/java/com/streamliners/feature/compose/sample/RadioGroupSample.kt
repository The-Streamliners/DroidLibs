package com.streamliners.feature.compose.sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.select.RadioGroup

@Composable
fun RadioGroupSample() {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {

        Column(
            modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            val gender = remember { mutableStateOf<String?>(null) }

            // MutableState passed directly
            RadioGroup(
                title = "Radio Group",
                state = gender,
                options = listOf("Male", "Female", "Other")
            )

            // Otherwise
            RadioGroup(
                title = "Radio Group",
                selection = gender.value,
                onSelectionChange = { gender.value = it },
                options = listOf("Male", "Female", "Other")
            )
        }
    }
}