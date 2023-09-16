package com.streamliners.compose.comp.select

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun LabelledCheckBox(
    modifier: Modifier = Modifier,
    state: MutableState<Boolean>,
    label: String,
    labelStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    background: Color? = null,
    padding: Dp = 8.dp,
    onCheckChanged: (Boolean) -> Unit = {}
) {

    var _modifier = modifier.clip(RoundedCornerShape(16.dp))
    background?.let { _modifier = _modifier.background(it) }

    Row(
        _modifier
            .clickable {
                state.value = !state.value
                onCheckChanged(state.value)
            }
            .padding(start = 4.dp, end = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Checkbox(
            checked = state.value,
            onCheckedChange = {
                state.value = it
                onCheckChanged(it)
            }
        )
        Spacer(modifier = Modifier.size(padding))
        Text(text = label, style = labelStyle)
    }

}

@ExperimentalMaterial3Api
@Composable
fun LabelledCheckBox(
    state: Boolean,
    onToggle: (Boolean) -> Unit,
    label: String,
    enabled: Boolean = true,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge
) {
    var modifier = Modifier
        .clip(RoundedCornerShape(4.dp))

    if (enabled) {
        modifier = modifier.clickable {
            onToggle(!state)
        }
    }

    modifier = modifier.padding(8.dp)

    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            modifier = Modifier.size(20.dp),
            checked = state,
            onCheckedChange = { onToggle(!state) },
            enabled = enabled
        )
        Spacer(modifier = Modifier.size(12.dp))
        Text(
            modifier = Modifier.alpha(
                if (enabled) 1f else ContentAlpha.disabled
            ),
            text = label,
            style = textStyle,
            color = Color.Black
        )
    }
}