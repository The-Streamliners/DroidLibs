package com.streamliners.feature.base_sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.compose.comp.appBar.TitleBarScaffold
import com.streamliners.feature.base_sample.comp.FactFetcherComp
import com.streamliners.feature.base_sample.comp.FactFetcherStandaloneComp
import com.streamliners.feature.base_sample.comp.FactFetcherWithLoadingButtonComp

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
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            FactFetcherComp(
                number = viewModel.numberInput1,
                fetchFactTask = viewModel.fetchFactTaskState1,
                fetchFact = viewModel::fetchFactUsingLoadingDialog
            )

            FactFetcherWithLoadingButtonComp(
                number = viewModel.numberInput2,
                fetchFactTask = viewModel.fetchFactTaskState2,
                fetchFact = viewModel::fetchFact
            )

            FactFetcherStandaloneComp(
                number = viewModel.numberInput3,
                fetchFactTask = viewModel.fetchFactTaskState3,
                fetchFact = viewModel::fetchFactUsingTaskExecutor
            )
        }
    }
}