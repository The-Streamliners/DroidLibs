package com.streamliners.feature.dialogs_sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.compose.android.comp.appBar.TitleBarScaffold
import com.streamliners.compose.android.comp.selectDialog.SelectDialog
import com.streamliners.compose.android.comp.selectDialog.rememberSelectDialogState
import com.streamliners.compose.android.comp.selectDialog.showForMultipleSelection
import com.streamliners.compose.android.comp.selectDialog.showForSingleSelection
import com.streamliners.compose.comp.Center
import com.streamliners.data.indianStatesAndUTs
import kotlinx.coroutines.launch

@Composable
fun DialogsSampleScreen(
    navController: NavController
) {

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    val selectDialogState = rememberSelectDialogState<String>()

    TitleBarScaffold(
        title = "Dialogs Samples",
        navigateUp = { navController.navigateUp() },
        snackbarHostState = snackbarHostState
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    selectDialogState.showForSingleSelection(
                        "Select your Gender",
                        listOf("Male", "Female", "Other")
                    ) { selection ->
                        scope.launch {
                            snackbarHostState.showSnackbar("You selected : $selection")
                        }
                    }
                }
            ) {
                Text(text = "Show Single Select Dialog")
            }

            Button(
                onClick = {
                    selectDialogState.showForMultipleSelection(
                        "Select your Favourite states",
                        indianStatesAndUTs
                    ) { selection ->
                        scope.launch {
                            snackbarHostState.showSnackbar("You selected : $selection")
                        }
                    }
                }
            ) {
                Text(text = "Show Multi Select Dialog")
            }
        }
    }

    SelectDialog(mState = selectDialogState)
}