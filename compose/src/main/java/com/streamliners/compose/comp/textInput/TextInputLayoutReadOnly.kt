package com.streamliners.compose.comp.textInput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.isError
import com.streamliners.compose.comp.textInput.state.update
import com.streamliners.compose.ext.noRippleClickable
import com.streamliners.compose.ext.outlinedTextFieldNormalColors

@ExperimentalMaterial3Api
@Composable
fun TextInputLayoutReadOnly(
    modifier: Modifier = Modifier,
    maxLength: Int = 100,
    state: MutableState<TextInputState>,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    trailingIconButton: @Composable (() -> Unit)? = null,
    onClick: () -> Unit
) {

    Column(modifier = modifier) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth()
                .noRippleClickable { onClick() },
            label = { Text(text = state.value.label) },
            value = state.value.value,
            onValueChange = {
                state.update(it)
            },
            leadingIcon = if (leadingIcon == null) null else {
                {
                    Icon(
                        imageVector = leadingIcon,
                        contentDescription = ""
                    )
                }
            },
            trailingIcon = if (trailingIcon == null && trailingIconButton == null) null else {
                {
                    if (trailingIconButton != null) {
                        trailingIconButton()
                    } else if (trailingIcon != null) {
                        Icon(
                            imageVector = trailingIcon,
                            contentDescription = ""
                        )
                    }
                }
            },
            enabled = false,
            colors = outlinedTextFieldNormalColors(),
            isError = state.value.isError()
        )

        state.value.error?.let {
            Spacer(modifier = Modifier.size(4.dp))
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.labelMedium
            )
        }
    }
}