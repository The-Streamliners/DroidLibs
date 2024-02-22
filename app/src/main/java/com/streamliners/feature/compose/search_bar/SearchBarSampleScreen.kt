package com.streamliners.feature.compose.search_bar

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.compose.comp.appBar.SearchAppBarScaffold
import com.streamliners.compose.comp.appBar.SearchAppBarState
import com.streamliners.compose.comp.appBar.open
import com.streamliners.data.CountryRepository

@Composable
fun SearchBarSampleScreen(
    navController: NavController,
    viewModel: SearchBarSampleViewModel
) {

    SearchAppBarScaffold(
        title = "Search AppBar Sample",
        searchHint = "Search country by name",
        navigateUp = { navController.navigateUp() },
        searchAppBarState = viewModel.searchAppBarState,
        onQueryChanged = viewModel::filter
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(12.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(viewModel.filteredCountries.value) {
                CountryCard(country = it)
            }
        }
    }
}

@Composable
fun CountryCard(country: CountryRepository.Country) {
    ElevatedCard(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.defaultMinSize(
                    minWidth = 48.dp
                ),
                text = country.code,
                style = MaterialTheme.typography.titleLarge
            )

            Column {
                Text(
                    text = country.name,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = country.region,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
    }
}