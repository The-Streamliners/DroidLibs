package com.streamliners.compose.android.comp

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.streamliners.compose.comp.FilledIconButtonSmall

@Composable
fun BottomSheet(
    title: String,
    state: MutableState<Boolean>,
    content: @Composable () -> Unit
) {
    BottomSheet(
        title = title,
        visible = state.value,
        onCloseRequest = { state.value = false },
        content = content
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomSheet(
    title: String,
    visible: Boolean,
    onCloseRequest: () -> Unit,
    content: @Composable () -> Unit
) {
    val bottomSheetState =
        rememberModalBottomSheetState(
            initialValue = ModalBottomSheetValue.Hidden,
            confirmValueChange = {
                if(it == ModalBottomSheetValue.Hidden)
                    onCloseRequest()
                true
            }
        )

    LaunchedEffect(key1 = visible) {
        if (visible) {
            bottomSheetState.show()
        } else {
            bottomSheetState.hide()
        }
    }

    BackHandler(enabled = visible) { onCloseRequest() }

    ModalBottomSheetLayout(
        modifier = Modifier.fillMaxWidth(),
        sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
        sheetState = bottomSheetState,
        sheetContent = {
            Content(title, visible, onCloseRequest, content)
        },
        content = { },
    )
}

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
private fun Content(
    title: String,
    visible: Boolean,
    onCloseRequest: () -> Unit,
    content: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.surface
    ) {

        Column(
            Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            Row(
                Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = title,
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.primary
                )

                val controller = LocalSoftwareKeyboardController.current
                FilledIconButtonSmall(
                    onClick = {
                        onCloseRequest()
                        controller?.hide()
                    },
                    icon = Icons.Default.Close,
                    colors = IconButtonDefaults.filledIconButtonColors(
                        containerColor = MaterialTheme.colorScheme.primary
                    )
                )
            }

            content()
        }
    }
}