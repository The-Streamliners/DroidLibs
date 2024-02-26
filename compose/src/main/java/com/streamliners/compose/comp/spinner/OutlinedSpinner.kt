package com.streamliners.compose.comp.spinner

import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.vector.ImageVector
import com.streamliners.compose.comp.spinner.state.SpinnerState
import com.streamliners.compose.comp.spinner.state.value
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.update
import com.streamliners.compose.comp.textInput.state.value

@Composable
fun OutlinedSpinner(
    modifier: Modifier = Modifier,
    options: List<String>,
    state: MutableState<TextInputState>,
    onStateChanged: (previousValue: String?) -> Unit = {},
    allowInput: Boolean = false,
    leadingIcon: ImageVector? = null,
) {
    val spinnerState = remember {
        SpinnerState(
            selection = mutableStateOf<String?>(null),
            textInputState = state,
            labelExtractor = { it }
        )
    }

    OutlinedSpinner(
        modifier = modifier,
        leadingIcon = leadingIcon,
        options = options,
        state = spinnerState,
        onStateChanged = onStateChanged,
        allowInput = allowInput
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> OutlinedSpinner(
    modifier: Modifier = Modifier,
    options: List<T>,
    state: SpinnerState<T>,
    onStateChanged: (previousValue: T?) -> Unit = {  },
    allowInput: Boolean = false,
    leadingIcon: ImageVector? = null,
) {

    var expanded by remember { mutableStateOf(false) }

    val filteredOptions = remember { mutableStateOf(options) }

    LaunchedEffect(key1 = state.textInputState.value) {
        val input = state.textInputState.value().lowercase()
        filteredOptions.value =
            if (allowInput && input.isNotBlank()) {
                options.filter { state.labelExtractor(it).lowercase().contains(input) }
            } else {
                options
            }
    }

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { expanded = it }
    ) {

        Row(
            Modifier.fillMaxWidth()
        ) {

            TextInputLayout(
                modifier = Modifier.fillMaxWidth(),
                state = state.textInputState,
                leadingIcon = leadingIcon,
                trailingIconButton = {
                    Icon(
                        imageVector = Icons.Filled.ArrowDropDown,
                        contentDescription = "Trailing icon for exposed dropdown menu",
                        modifier = Modifier.rotate(if (expanded) 180f else 360f)
                            .clickable(false) {}
                            .focusable(false)
                    )
                },
                readOnly = !allowInput
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            filteredOptions.value.forEach {
                DropdownMenuItem(
                    onClick = {
                        state.textInputState.update(state.labelExtractor(it))
                        val previousValue = state.value()
                        state.selection.value = it
                        if (previousValue != it) {
                            onStateChanged(previousValue)
                        }
                        expanded = false
                    },
                    content = {
                        Text(text = state.labelExtractor(it))
                    }
                )
            }
        }
    }
}