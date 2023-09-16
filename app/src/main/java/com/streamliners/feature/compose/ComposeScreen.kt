package com.streamliners.feature.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.compose.comp.appBar.TitleBarScaffold

@ExperimentalMaterial3Api
@Composable
fun ComposeScreen(
    navController: NavController
) {
    TitleBarScaffold(
        title = "DroidLibs Sample",
        navigateUp = { navController.navigateUp() }
    ) { paddingValues ->

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

        }
    }
}