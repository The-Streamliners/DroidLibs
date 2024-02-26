package com.streamliners.feature.official_sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.compose.comp.appBar.TitleBarScaffold
import com.streamliners.ui.main.Route

@Composable
fun OfficialSamplesScreen(
    navController: NavController,
    showMessageDialog: (String, String) -> Unit
) {
    TitleBarScaffold(
        title = "Official Samples",
        navigateUp = { navController.navigateUp() }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Following components are not a part of DroidLibs library. These are samples of AndroidX M3 library components.",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center
            )
            
            OfficialDatePickerSample(
                showMessageDialog = showMessageDialog
            )

            OfficialTimePickerSample(
                showMessageDialog = showMessageDialog
            )

            Button(
                onClick = {
                    navController.navigate(Route.OfficialSearchBarSampleScreen.route)
                }
            ) {
                Text(text = "Search Bar")
            }
        }
    }
}