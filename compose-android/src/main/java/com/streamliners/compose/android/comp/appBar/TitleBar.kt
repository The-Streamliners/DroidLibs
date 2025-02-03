package com.streamliners.compose.android.comp.appBar

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TitleBarScaffold(
    title: String,
    navigationIcon: (@Composable () -> Unit)? = null,
    navigateUp: (() -> Unit)? = null,
    snackbarHostState: SnackbarHostState? = null,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = containerColor,
        contentColor = contentColor,
        topBar = {
            TitleBar(
                title, navigationIcon, navigateUp, actions = actions
            )
        },
        snackbarHost = {
            snackbarHostState?.let { SnackbarHost(hostState = it) }
        }
    ) { paddingValues ->

        content(paddingValues)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TitleBar(
    title: String,
    navigationIcon: (@Composable () -> Unit)? = null,
    navigateUp: (() -> Unit)? = null,
    colors: TopAppBarColors = topAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        navigationIconContentColor = Color.White,
        titleContentColor = Color.White,
        actionIconContentColor = Color.White
    ),
    actions: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                modifier = Modifier.basicMarquee(),
                text = title,
                maxLines = 1
            )
        },
        navigationIcon = {
            navigationIcon?.invoke() ?:
            navigateUp?.let {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                }
            }
        },
        colors = colors,
        actions = actions
    )
}
