package com.streamliners.feature.base_sample

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
import com.streamliners.base.ext.execute
import com.streamliners.base.ext.hideLoader
import com.streamliners.base.ext.showConfirmationDialog
import com.streamliners.base.ext.showLoader
import com.streamliners.base.ext.showMessageDialog
import com.streamliners.base.uiEvent.UiEvent
import com.streamliners.compose.android.comp.appBar.TitleBarScaffold
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BaseSampleScreen(
    navController: NavController,
    viewModel: BaseSampleViewModel
) {
    TitleBarScaffold(
        title = "Base Sample",
        navigateUp = { navController.navigateUp() }
    ) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    viewModel.execute {
                        viewModel.showLoader()
                        delay(3000)
                        viewModel.hideLoader()
                    }
                }
            ) {
                Text(text = "General loading dialog")
            }

            Button(
                onClick = {
                    viewModel.execute {
                        viewModel.showLoader("Launching rocket")
                        delay(3000)
                        viewModel.hideLoader()
                    }
                }
            ) {
                Text(text = "Message loading dialog")
            }

            Button(
                onClick = {
                    viewModel.showMessageDialog(
                        UiEvent.ShowMessageDialog(
                            title = "Unsaved changes",
                            message = "Changes made will be discarded. Sure to exit?",
                            isCancellable = false,
                            positiveButton = UiEvent.DialogButton(
                                label = "YES",
                                dismissOnClick = true
                            ),
                            negativeButton = UiEvent.DialogButton(
                                label = "NO",
                                dismissOnClick = true
                            )
                        )
                    )
                }
            ) {
                Text(text = "Message dialog")
            }

            Button(
                onClick = {
                    viewModel.showConfirmationDialog(
                        title = "Confirm delete",
                        message = "Are you sure? This can't be undone!",
                        onConfirm = {}
                    )
                }
            ) {
                Text(text = "Confirmation dialog")
            }
        }
    }
}