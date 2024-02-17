package com.streamliners.feature.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.base.BaseActivity
import com.streamliners.base.ext.showMessageDialog
import com.streamliners.base.ext.showToast
import com.streamliners.compose.comp.appBar.TitleBarScaffold
import com.streamliners.compose.comp.textInput.config.InputConfig
import com.streamliners.compose.comp.textInput.config.fixedLengthNumber
import com.streamliners.compose.comp.textInput.config.number
import com.streamliners.compose.comp.textInput.dialog.TextInputDialog
import com.streamliners.compose.comp.textInput.dialog.TextInputDialogState
import com.streamliners.compose.comp.textInput.dialog.rememberTextInputDialogState
import com.streamliners.compose.comp.textInput.state.TextInputState
import com.streamliners.ui.main.Route
import java.util.Calendar
import java.util.Calendar.YEAR

@ExperimentalMaterial3Api
@Composable
fun BaseActivity.ComposeScreen(
    navController: NavController
) {
    TitleBarScaffold(
        title = "DroidLibs Sample",
        navigateUp = { navController.navigateUp() }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate(Route.TextInputLayoutScreen.route) }
            ) {
                Text(text = "Text Input Layout")
            }

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
                            val age = Calendar.getInstance().get(YEAR) - birthYear
                            showMessageDialog("Age", "Your age is $age years.")
                        }
                    )
                }
            ) {
                Text(text = "Text Input Dialog")
            }
        }
    }
}