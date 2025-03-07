@file:OptIn(ExperimentalMaterial3Api::class)

package com.streamliners.feature.compose

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.base.BaseActivity
import com.streamliners.base.ext.showToast
import com.streamliners.compose.android.comp.drawing_pad.DrawingPad
import com.streamliners.compose.android.comp.appBar.TitleBarScaffold
import com.streamliners.compose.android.comp.drawing_pad.capture
import com.streamliners.compose.android.comp.drawing_pad.rememberDrawingPadState

@Composable
fun BaseActivity.DrawingPadSampleScreen(
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
            val state = rememberDrawingPadState()

            DrawingPad(
                modifier = Modifier.fillMaxSize(),
                state = state
            )

            FloatingActionButton(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(36.dp),
                onClick = {
                    if (state.isBlank()) {
                        showToast("Please draw something!")
                    } else {
                        state.capture { bitmap ->
                            saveAndShareImage(bitmap)
                        }
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