package com.streamliners.compose.comp.select

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RadioGroup(
    modifier: Modifier = Modifier,
    title: String? = null,
    state: MutableState<String?>,
    options: List<String>
) {
    RadioGroup(
        modifier = modifier,
        title = title,
        selection = state.value,
        onSelectionChange = { state.value = it },
        options = options
    )
}

@Composable
fun RadioGroup(
    modifier: Modifier = Modifier,
    title: String? = null,
    selection: String?,
    onSelectionChange: (String) -> Unit,
    options: List<String>
) {
    Column(
        modifier = modifier
    ) {
        title?.let {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = it,
                style = MaterialTheme.typography.titleLarge
            )
        }

        options.forEach { option ->
            LabelledRadioButton(
                label = option,
                selected = selection == option,
                onClick = { onSelectionChange(option) }
            )
        }
    }
}