package com.streamliners.feature.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.compose.comp.Center
import com.streamliners.compose.comp.appBar.TitleBarScaffold
import com.streamliners.ui.main.Route

@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    navController: NavController
) {
    TitleBarScaffold(
        title = "DroidLibs Sample"
    ) { paddingValues ->

        Center(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
        ) {
            Button(
                onClick = {
                    navController.navigate(Route.BaseSampleScreen.route)
                }
            ) {
                Text(text = "Base Sample")
            }

            Button(
                onClick = {
                    navController.navigate(Route.ComposeScreen.route)
                }
            ) {
                Text(text = "Compose")
            }

            Button(
                onClick = {
                    navController.navigate(Route.OfficialSamplesScreen.route)
                }
            ) {
                Text(text = "AndroidX M3 Samples")
            }
        }
    }
}