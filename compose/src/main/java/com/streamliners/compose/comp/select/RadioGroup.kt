package com.streamliners.compose.comp.select

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

fun <T> List<T>.containsAnyOf(
    vararg options: T
): Boolean {
    return options.any { contains(it) }
}

enum class Layout { Row, Column }

@Composable
fun RadioGroup(
    modifier: Modifier = Modifier,
    title: String? = null,
    state: MutableState<String?>,
    options: List<String>,
    enabled: Boolean = true,
    layout: Layout = Layout.Column
) {
    RadioGroup(
        modifier = modifier,
        title = title,
        selection = state.value,
        onSelectionChange = { state.value = it },
        options = options,
        labelExtractor = { it },
        enabled = enabled,
        layout = layout
    )
}

@Composable
fun RadioGroup(
    modifier: Modifier = Modifier,
    title: String? = null,
    selection: String?,
    onSelectionChange: (String) -> Unit,
    options: List<String>,
    enabled: Boolean = true,
    layout: Layout = Layout.Column
) {
    RadioGroup(
        modifier = modifier,
        title = title,
        selection = selection,
        onSelectionChange = onSelectionChange,
        options = options,
        labelExtractor = { it },
        enabled = enabled,
        layout = layout
    )
}

@Composable
fun <T> RadioGroup(
    modifier: Modifier = Modifier,
    title: String? = null,
    state: MutableState<T?>,
    options: List<T>,
    labelExtractor: (T) -> String,
    enabled: Boolean = true,
    layout: Layout = Layout.Column
) {
    RadioGroup(
        modifier = modifier,
        title = title,
        selection = state.value,
        onSelectionChange = { state.value = it },
        options = options,
        labelExtractor = labelExtractor,
        enabled = enabled,
        layout = layout
    )
}

@Composable
fun <T> RadioGroup(
    modifier: Modifier = Modifier,
    title: String? = null,
    selection: T?,
    onSelectionChange: (T) -> Unit,
    options: List<T>,
    labelExtractor: (T) -> String,
    enabled: Boolean = true,
    layout: Layout = Layout.Column
) {
    val content: @Composable () -> Unit = {
        title?.let {
            Text(
                modifier = Modifier.padding(bottom = 8.dp),
                text = it,
                style = MaterialTheme.typography.titleLarge
            )
        }

        options.forEach { option ->
            LabelledRadioButton(
                label = labelExtractor(option),
                selected = selection == option,
                onClick = { onSelectionChange(option) },
                enabled = enabled
            )
        }
    }

    if (layout == Layout.Column) {
        Column(modifier, content = { content() })
    } else {
        Row(modifier, content = { content() })
    }
}

@Composable
fun LabelledRadioButton(
    label: String,
    selected: Boolean,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    whiteTint: Boolean = false,
    color: Color = Color.Black,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    val colors = if (whiteTint) {
        RadioButtonDefaults.colors(
            selectedColor = Color.White,
            unselectedColor = Color.White
        )
    } else {
        RadioButtonDefaults.colors()
    }

    Row(
        modifier = Modifier
            .clip(RoundedCornerShape(16.dp))
            .run { if (enabled) clickable { onClick() } else this }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            modifier = Modifier.size(20.dp),
            selected = selected,
            onClick = onClick,
            colors = colors,
            enabled = enabled
        )
        Spacer(modifier = Modifier.size(12.dp))

        Text(
            text = label,
            style = textStyle,
            color = color.copy(alpha = if (enabled) 1f else 0.38f)
        )
    }
}