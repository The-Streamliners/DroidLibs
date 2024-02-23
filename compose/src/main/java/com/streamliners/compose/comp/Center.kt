package com.streamliners.compose.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun Center(
    modifier: Modifier = Modifier.fillMaxSize().padding(16.dp),
    content: @Composable ColumnScope.() -> Unit
) {
    Column(
        modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        content()
    }
}

@Composable
fun CenterText(
    modifier: Modifier = Modifier.fillMaxSize().padding(16.dp),
    text: String,
    style: TextStyle = MaterialTheme.typography.titleMedium
) {
    Center(modifier) {
        Text(
            text = text,
            style = style
        )
    }
}