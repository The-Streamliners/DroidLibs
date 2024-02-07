package com.streamliners.base.taskState.comp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import com.streamliners.base.taskState.TaskState

@Composable
fun <T> TaskLoadingButton(
    modifier: Modifier = Modifier,
    state: MutableState<TaskState<T>>,
    label: String,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = state.value !is TaskState.Loading,
        onClick = onClick
    ) {
        Box(
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier.alpha(
                    if (state.value !is TaskState.Loading) 1f else 0f
                ),
                text = label
            )

            state.whenLoading {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp
                )
            }
        }
    }
}