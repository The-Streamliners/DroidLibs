package com.streamliners.feature.compose.sample

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.streamliners.base.BaseActivity
import com.streamliners.base.ext.showMessageDialog
import com.streamliners.compose.comp.textInput.config.InputConfig
import com.streamliners.compose.comp.textInput.config.fixedLengthNumber
import com.streamliners.compose.comp.textInput.dialog.TextInputDialog
import com.streamliners.compose.comp.textInput.dialog.TextInputDialogState
import com.streamliners.compose.comp.textInput.dialog.rememberTextInputDialogState
import com.streamliners.compose.comp.textInput.state.TextInputState
import java.util.Calendar

@Composable
fun BaseActivity.TextInputDialogSample() {
    val textInputDialogState = rememberTextInputDialogState()
    TextInputDialog(state = textInputDialogState)

    Button(
        onClick = {
            textInputDialogState.value = TextInputDialogState.Visible(
                title = "Enter your birth year",
                input = mutableStateOf(
                    TextInputState(
                        label = "Year",
                        inputConfig = InputConfig.fixedLengthNumber(4)
                    )
                ),
                submit = { input ->
                    val birthYear = input.toInt()
                    val age = Calendar.getInstance().get(Calendar.YEAR) - birthYear
                    showMessageDialog("Age", "Your age is $age years.")
                }
            )
        }
    ) {
        Text(text = "Text Input Dialog")
    }
}