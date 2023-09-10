package com.streamliners.compose.comp.spinner

import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.gestures.ScrollableState
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ExposedDropdownMenuBox
import androidx.compose.material.ExposedDropdownMenuDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.TextInputState
import com.streamliners.compose.comp.textInput.update

@ExperimentalMaterialApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OutlinedSpinner(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    options: List<String>,
    parentScrollState: ScrollableState? = null,
    state: MutableState<TextInputState>,
    onStateChanged: () -> Unit = {}
) {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { if(parentScrollState?.isScrollInProgress != true) expanded = it }
    ) {

        Row(
            Modifier.fillMaxWidth()
        ) {

            TextInputLayout(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = icon,
                trailingIconButton = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                state = state,
                readOnly = true
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            options.forEach {
                DropdownMenuItem(
                    onClick = {
                        state.update(it)
                        onStateChanged()
                        expanded = false
                    },
                    content = {
                        Text(text = it)
                    }
                )
            }
        }
    }


}

@ExperimentalMaterialApi
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> OutlinedSpinner(
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    options: List<T>,
    state: SelectedSuggestion<T>,
    parentScrollState: ScrollState? = null,
    onStateChanged: (T) -> Unit = {  }
) {

    var expanded by remember { mutableStateOf(false) }

    ExposedDropdownMenuBox(
        modifier = modifier.fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { if(parentScrollState?.isScrollInProgress != true) expanded = it }
    ) {

        Row(
            Modifier.fillMaxWidth()
        ) {

            TextInputLayout(
                modifier = Modifier.fillMaxWidth(),
                leadingIcon = icon,
                trailingIconButton = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expanded
                    )
                },
                state = state.textInputState,
                readOnly = true
            )
        }

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {

            options.forEach {
                DropdownMenuItem(
                    onClick = {
                        state.textInputState.update(state.labelExtractor(it))
                        val previousValue = state.value()
                        state.state.value = it
                        if (previousValue != it) {
                            onStateChanged(it)
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