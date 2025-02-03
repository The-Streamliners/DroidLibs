package com.streamliners.compose.android.comp.selectDialog

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.streamliners.compose.comp.textInput.state.hasValidInput
import com.streamliners.compose.comp.textInput.state.value

sealed interface SelectDialogState <T> {
    data class Visible<T>(
        val title: String,
        val allowMultiple: Boolean,
        val options: List<T>,
        val labelExtractor: (T) -> String,
        val callback: (List<T>) -> Unit
    ): SelectDialogState<T>

    class Hidden<T>: SelectDialogState<T>
}

@Composable
fun <T> rememberSelectDialogState(): MutableState<SelectDialogState<T>> {
    return remember { mutableStateOf(SelectDialogState.Hidden()) }
}

fun <T> MutableState<SelectDialogState<T>>.showForSingleSelection(
    title: String,
    options: List<T>,
    labelExtractor: (T) -> String,
    callback: (T) -> Unit
) {
    value = SelectDialogState.Visible(
        title, false, options, labelExtractor
    ) {
        callback(it.first())
    }
}

fun MutableState<SelectDialogState<String>>.showForSingleSelection(
    title: String,
    options: List<String>,
    callback: (String) -> Unit
) {
    value = SelectDialogState.Visible(
        title, false, options, { it }
    ) {
        callback(it.first())
    }
}

fun <T> MutableState<SelectDialogState<T>>.showForMultipleSelection(
    title: String,
    options: List<T>,
    labelExtractor: (T) -> String,
    callback: (List<T>) -> Unit
) {
    value = SelectDialogState.Visible(
        title, true, options, labelExtractor, callback
    )
}

fun MutableState<SelectDialogState<String>>.showForMultipleSelection(
    title: String,
    options: List<String>,
    callback: (List<String>) -> Unit
) {
    value = SelectDialogState.Visible(
        title, true, options, { it }, callback
    )
}

fun <T> MutableState<SelectDialogState<T>>.hide() {
    value = SelectDialogState.Hidden()
}

@Composable
fun <T> SelectDialog(
    mState: MutableState<SelectDialogState<T>>
) {
    val state = (mState.value as? SelectDialogState.Visible<T>) ?: return

    val selection = remember { SnapshotStateList<T>() }

    val emptySelectionError = remember { mutableStateOf(false) }

    AlertDialog(
        modifier = Modifier
            .padding(28.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        properties = DialogProperties(
            usePlatformDefaultWidth = false,
            dismissOnClickOutside = false,
            dismissOnBackPress = false
        ),
        onDismissRequest = { mState.hide() },
        title = {
            Text(text = state.title)
        },
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                item {
                    AnimatedVisibility(visible = emptySelectionError.value) {
                        Text(
                            modifier = Modifier.padding(8.dp),
                            text = "Please select ${if (state.allowMultiple) "one or more options" else "an option"} first!",
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }

                items(state.options) { item ->
                    ItemCard(
                        item = item,
                        state = state,
                        selected = selection.contains(item),
                        onSelect = {
                            if (selection.contains(item)) {
                                selection.remove(item)
                            } else {
                                if (!state.allowMultiple) {
                                    selection.clear()
                                }

                                selection.add(item)
                                emptySelectionError.value = false
                            }
                        }
                    )
                }
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    if (selection.isEmpty()) {
                        emptySelectionError.value = true
                    } else {
                        state.callback(selection)
                        mState.hide()
                    }
                }
            ) {
                Text("SUBMIT")
            }
        },
        dismissButton = {
            TextButton(
                onClick = { mState.hide() }
            ) {
                Text("CANCEL")
            }
        }
    )
}

@Composable
fun <T> ItemCard(
    item: T,
    state: SelectDialogState.Visible<T>,
    selected: Boolean,
    onSelect: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .clickable { onSelect() }
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        if (state.allowMultiple) {
            Checkbox(
                modifier = Modifier.size(20.dp),
                checked = selected,
                onCheckedChange = { onSelect() }
            )
        } else {
            RadioButton(
                modifier = Modifier.size(20.dp),
                selected = selected,
                onClick = onSelect
            )
        }

        Spacer(
            modifier = Modifier.size(12.dp)
        )

        Text(
            text = state.labelExtractor(item),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}