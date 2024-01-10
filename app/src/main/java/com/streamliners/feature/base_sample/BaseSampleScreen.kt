package com.streamliners.feature.base_sample

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.compose.comp.appBar.TitleBarScaffold
import com.streamliners.feature.base_sample.comp.FactFetcherComp

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
                .padding(paddingValues)
                .padding(16.dp)
        ) {

            FactFetcherComp(
                number = viewModel.number,
                fact = viewModel.fact,
                fetchFact = viewModel::fetchFactUsingLoadingDialog
            )
        }
    }
}