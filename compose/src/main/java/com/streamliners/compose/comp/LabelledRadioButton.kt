package com.streamliners.compose.comp

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun LabelledRadioButton(
    label: String,
    selected: Boolean,
    textStyle: TextStyle = MaterialTheme.typography.bodyLarge,
    whiteTint: Boolean = false,
    color: Color = Color.Black,
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
            .clickable { onClick() }
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        RadioButton(
            modifier = Modifier.size(20.dp),
            selected = selected,
            onClick = onClick,
            colors = colors
        )
        Spacer(modifier = Modifier.size(12.dp))

        Text(text = label, style = textStyle, color = color)
    }
}