package com.streamliners.compose.comp.textInput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.isError
import com.streamliners.compose.comp.textInput.state.preValidateAndUpdate

@ExperimentalMaterial3Api
@Composable
fun TextInputLayout(
    modifier: Modifier = Modifier.fillMaxWidth(),
    state: MutableState<TextInputState>,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    trailingIconButton: @Composable() (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    showLabel: Boolean = true,
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors(),
    keyboardOptions: KeyboardOptions? = null,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = true,
    imeAction: ImeAction = ImeAction.Default,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextChanged: () -> Unit = {}
) {
    Column(modifier = modifier) {

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = if (!showLabel) null else {
                {
                    Text(
                        text = buildAnnotatedString {
                            append(state.value.label)
                            if (!state.value.inputConfig.optional) {
                                withStyle(
                                    SpanStyle(color = MaterialTheme.colorScheme.error)
                                ) {
                                    " *"
                                }
                            }
                        }
                    )
                }
            },
            value = state.value.value,
            onValueChange = {
                state.preValidateAndUpdate(it)
                onTextChanged()
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
            enabled = enabled,
            readOnly = readOnly,
            colors = colors,
            isError = state.value.isError(),
            keyboardOptions = (keyboardOptions ?: KeyboardOptions.Default).copy(
                keyboardType = state.value.inputConfig.keyboardType,
                imeAction = imeAction
            ),
            singleLine = singleLine,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation,
            maxLines = if (singleLine) 1 else Int.MAX_VALUE
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