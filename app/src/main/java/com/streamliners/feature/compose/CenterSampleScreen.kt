package com.streamliners.feature.compose

import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.streamliners.compose.comp.Center
import com.streamliners.compose.android.comp.appBar.TitleBarScaffold

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CenterSampleScreen(
    navController: NavController
) {
    TitleBarScaffold(
        title = "Center composable",
        navigateUp = { navController.navigateUp() }
    ) {
        Center {
            Button(
                onClick = { navController.navigateUp() }
            ) {
                Text(text = "Go back")
            }
        }
    }
}