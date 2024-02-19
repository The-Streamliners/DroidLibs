package com.streamliners.feature.compose.text_input_layout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.streamliners.compose.comp.textInput.config.email
import com.streamliners.compose.comp.textInput.config.fixedLengthNumber
import com.streamliners.compose.comp.textInput.config.number
import com.streamliners.compose.comp.textInput.config.password
import com.streamliners.compose.comp.textInput.config.text
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.compose.comp.textInput.state.allHaveValidInputs

@ExperimentalMaterial3Api
@Composable
fun TextInputLayoutSamples(
    modifier: Modifier = Modifier
) {
    val nameInput = remember {
        mutableStateOf(
            TextInputState(
                label = "Name",
                inputConfig = InputConfig.text {
                    optional = true
                    minLength = 5
                    maxLength = 30
                }
            )
        )
    }

    val passwordInput = remember {
        mutableStateOf(
            TextInputState(
                label = "Password",
                inputConfig = InputConfig.password()
            )
        )
    }

    val ageInput = remember {
        mutableStateOf(
            TextInputState(
                label = "Age",
                inputConfig = InputConfig.number {
                    maxLength = 3
                }
            )
        )
    }

    val contactNoInput = remember {
        mutableStateOf(
            TextInputState(
                label = "Contact number",
                inputConfig = InputConfig.fixedLengthNumber(10)
            )
        )
    }

    val emailInput = remember {
        mutableStateOf(
            TextInputState(
                label = "Email Address",
                inputConfig = InputConfig.email {
                    minLength = 10
                }
            )
        )
    }

    val aadharNoInput = remember {
        mutableStateOf(
            TextInputState(
                label = "Aadhar number",
                inputConfig = InputConfig.fixedLengthNumber(12)
            )
        )
    }

    val panNoInput = remember {
        mutableStateOf(
            TextInputState(
                label = "PAN number",
                inputConfig = InputConfig.text {
                    maxLength = 10
                    strictMaxLengthCheck = true
                    regexValidation = InputConfig.RegexValidation(
                        Regex("^[A-Z]{5}\\d{4}[A-Z]{1}$")
                    )
                }
            )
        )
    }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {

        TextInputLayout(state = nameInput)

        TextInputLayout(state = ageInput)

        TextInputLayout(state = contactNoInput)

        TextInputLayout(state = emailInput)

        TextInputLayout(state = aadharNoInput)

        TextInputLayout(state = panNoInput)

        TextInputLayout(state = passwordInput)

        Button(
            modifier = Modifier.align(Alignment.CenterHorizontally),
            onClick = {
                if (
                    TextInputState.allHaveValidInputs(
                        nameInput, ageInput, contactNoInput, emailInput, aadharNoInput, panNoInput, passwordInput
                    )
                ) {
//                    viewModel.registerUser(
//                        User(
//                            name = nameInput.value(),
//                            age = ageInput.value().toInt(),
//                            contactNo = contactNoInput.value(),
//                            email = emailInput.value(),
//                            aadharNo = aadharNoInput.value(),
//                            panNo = panNoInput.value()
//                        )
//                    )
                }
            }
        ) {
            Text(text = "SUBMIT")
        }
    }
}