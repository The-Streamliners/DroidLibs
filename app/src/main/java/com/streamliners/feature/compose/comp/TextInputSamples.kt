package com.streamliners.feature.compose.comp

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.textInput.TextInputLayout
import com.streamliners.compose.comp.textInput.config.InputConfig
import com.streamliners.compose.comp.textInput.config.InputConfig.RegexValidation
import com.streamliners.compose.comp.textInput.config.email
import com.streamliners.compose.comp.textInput.config.fixedLengthNumber
import com.streamliners.compose.comp.textInput.config.number
import com.streamliners.compose.comp.textInput.config.text
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.areAllValid

@ExperimentalMaterial3Api
@Composable
fun TextInputSamples() {

    val name = remember {
        mutableStateOf(
            TextInputState("Name")
        )
    }

    val age = remember {
        mutableStateOf(
            TextInputState(
                label = "Age",
                inputConfig = InputConfig.number {
                    maxLength = 3
                }
            )
        )
    }

    val contactNo = remember {
        mutableStateOf(
            TextInputState(
                label = "Contact number",
                inputConfig = InputConfig.fixedLengthNumber(10)
            )
        )
    }

    val email = remember {
        mutableStateOf(
            TextInputState(
                label = "Email Address",
                inputConfig = InputConfig.email()
            )
        )
    }

    val aadhar = remember {
        mutableStateOf(
            TextInputState(
                label = "Aadhar number",
                inputConfig = InputConfig.fixedLengthNumber(12)
            )
        )
    }

    val pan = remember {
        mutableStateOf(
            TextInputState(
                label = "PAN number",
                inputConfig = InputConfig.text {
                    maxLength = 10
                    strictMaxLengthCheck = true
                    regexValidation = RegexValidation(
                        Regex("^[A-Z]{5}\\d{4}[A-Z]{1}$")
                    )
                }
            )
        )
    }

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        TextInputLayout(state = name)

        TextInputLayout(state = age)

        TextInputLayout(state = contactNo)

        TextInputLayout(state = email)

        TextInputLayout(state = aadhar)

        TextInputLayout(state = pan)

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                val valid = TextInputState.areAllValid(
                    name, age, contactNo, email, aadhar, pan
                )

                if (valid) {

                }
            }
        ) {
            Text(text = "SUBMIT")
        }
    }
}