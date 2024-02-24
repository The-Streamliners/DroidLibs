package com.streamliners.feature.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.navigation.NavController
import com.streamliners.compose.comp.DrawingPad
import com.streamliners.compose.comp.appBar.TitleBarScaffold
import com.streamliners.compose.comp.capture
import com.streamliners.compose.comp.rememberCaptureState

@Composable
fun DrawingPadSampleScreen(
    navController: NavController
) {
    TitleBarScaffold(
        title = "DrawingPad Sample",
        navigateUp = { navController.navigateUp() }
    ) { paddingValues ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            val captureState = rememberCaptureState()
            val path = remember { mutableStateOf(Path()) }

            DrawingPad(
                modifier = Modifier.fillMaxSize(),
                captureState = captureState,
                path = path
            )

            FloatingActionButton(
                onClick = {
                    captureState.capture { bitmap ->

                    }
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = "Capture & Share"
                )
            }
        }
    }
}