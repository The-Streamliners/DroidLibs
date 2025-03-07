package com.streamliners.feature.task_state_sample

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.streamliners.compose.android.comp.appBar.TitleBarScaffold
import com.streamliners.feature.task_state_sample.comp.FactFetcherComp
import com.streamliners.feature.task_state_sample.comp.FactFetcherStandaloneComp
import com.streamliners.feature.task_state_sample.comp.FactFetcherWithLoadingButtonComp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskStateSampleScreen(
    navController: NavController,
    viewModel: TaskStateSampleViewModel
) {
    TitleBarScaffold(
        title = "TaskState Sample",
        navigateUp = { navController.navigateUp() }
    ) { paddingValues ->

        Column(
            Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Approach 1 : Standalone
            FactFetcherStandaloneComp(
                number = viewModel.numberInput3,
                fetchFactTask = viewModel.fetchFactTaskState3,
                fetchFact = viewModel::fetchFactUsingTaskExecutor
            )

            // Approach 2 : Delegate error handling
            FactFetcherWithLoadingButtonComp(
                number = viewModel.numberInput2,
                fetchFactTask = viewModel.fetchFactTaskState2,
                fetchFact = viewModel::fetchFact
            )

            // Approach 3 : Delegate error handling & loader
            FactFetcherComp(
                number = viewModel.numberInput1,
                fetchFactTask = viewModel.fetchFactTaskState1,
                fetchFact = viewModel::fetchFactUsingLoadingDialog
            )

        }
    }
}