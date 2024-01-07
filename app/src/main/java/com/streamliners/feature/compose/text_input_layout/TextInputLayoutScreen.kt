package com.streamliners.feature.compose.text_input_layout

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.streamliners.compose.comp.appBar.TitleBarScaffold

@ExperimentalMaterial3Api
@Composable
fun TextInputLayoutScreen(
    navController: NavController
) {

    TitleBarScaffold(
        title = "TextInputLayout Sample",
        navigateUp = { navController.navigateUp() }
    ) { paddingValues ->

        TextInputLayoutSamples(
            modifier = Modifier.padding(paddingValues)
        )
    }
}