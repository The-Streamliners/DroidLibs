package com.streamliners.feature.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.base.BaseActivity
import com.streamliners.compose.comp.appBar.TitleBarScaffold
import com.streamliners.feature.compose.sample.LabelledCheckBoxSample
import com.streamliners.feature.compose.sample.LabelledRadioButtonSample
import com.streamliners.feature.compose.sample.OutlinedSpinnerSample
import com.streamliners.feature.compose.sample.RadioGroupSample
import com.streamliners.feature.compose.sample.TextInputDialogSample
import com.streamliners.ui.main.Route

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
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = { navController.navigate(Route.TextInputLayoutScreen.route) }
            ) {
                Text(text = "Text Input Layout")
            }

            TextInputDialogSample()

            LabelledCheckBoxSample()

            RadioGroupSample()

            LabelledRadioButtonSample()

            OutlinedSpinnerSample()
        }
    }
}