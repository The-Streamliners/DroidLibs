package com.streamliners.compose.comp.appBar

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ContentAlpha
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.streamliners.compose.comp.appBar.SearchAppBarState.*

sealed class SearchAppBarState {
    data object Closed: SearchAppBarState()
    data class Opened(
        val query: String = ""
    ): SearchAppBarState()

    fun isFilterActive() = this is Opened && this.query.isNotEmpty()

    fun query() = (this as? Opened)?.query ?: ""
}

@Composable
fun rememberSearchAppBarState(): MutableState<SearchAppBarState> {
    return remember { mutableStateOf(Closed) }
}

fun ViewModel.searchAppBarState(): MutableState<SearchAppBarState> {
    return mutableStateOf(Closed)
}

@Composable
fun SearchAppBarScaffold(
    title: String,
    searchHint: String = "",
    searchAppBarState: MutableState<SearchAppBarState>,
    navigateUp: (() -> Unit)? = null,
    onQueryChanged: (String) -> Unit,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        Modifier.fillMaxSize(),
        topBar = {
            SearchAppBar(title, searchHint, navigateUp, searchAppBarState, onQueryChanged)
        }
    ) {
        content(it)
    }
}

@Composable
fun SearchAppBar(
    title: String,
    searchHint: String = "",
    navigateUp: (() -> Unit)? = null,
    searchAppBarState: MutableState<SearchAppBarState>,
    onQueryChanged: (String) -> Unit,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val state = searchAppBarState.value

    when (state) {

        Closed -> {
            TitleBar(
                title = title,
                navigateUp = navigateUp,
                actions = {
                    IconButton(
                        onClick = { searchAppBarState.value = Opened() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = "",
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    actions()
                }
            )
        }

        is Opened -> {
            Row(
                Modifier
                    .fillMaxWidth()
                    .height(64.dp)
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OpenedSearchAppBar(
                    state = state,
                    searchHint = searchHint,
                    onQueryChanged = {
                        searchAppBarState.value = state.copy(query = it)
                        onQueryChanged(it)
                    },
                    close = {
                        searchAppBarState.value = Closed
                    }
                )
            }
        }
    }
}

@Composable
private fun OpenedSearchAppBar(
    state: Opened,
    searchHint: String = "Search here...",
    onQueryChanged: (String) -> Unit,
    close: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(key1 = Unit) { focusRequester.requestFocus() }

    BackHandler { close() }

    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        // Search icon
        Icon(
            modifier = Modifier.alpha(ContentAlpha.medium),
            imageVector = Icons.Default.Search,
            contentDescription = "Search",
            tint = Color.White
        )

        // Text
        Box(
            modifier = Modifier.weight(1f)
        ) {

            // Placeholder
            androidx.compose.animation.AnimatedVisibility (
                modifier = Modifier.padding(start = 4.dp),
                visible = state.query.isBlank()
            ) {
                Text(
                    text = searchHint,
                    style = MaterialTheme.typography.bodyLarge.copy(color = Color.White.copy(alpha = ContentAlpha.medium))
                )
            }

            // Search TextField
            BasicTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .focusRequester(focusRequester),
                value = state.query,
                onValueChange = { onQueryChanged(it) },
                textStyle = MaterialTheme.typography.bodyLarge.copy(color = Color.White),
                cursorBrush = SolidColor(Color.White.copy(alpha = ContentAlpha.medium)),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onQueryChanged(state.query)
                    }
                )
            )
        }

        // Close icon
        Icon(
            modifier = Modifier.clickable {
                if (state.query.isNotEmpty()) {
                    onQueryChanged("")
                } else {
                    close()
                }
            },
            imageVector = Icons.Default.Close,
            contentDescription = "Clear / Close",
            tint = Color.White
        )
    }
}