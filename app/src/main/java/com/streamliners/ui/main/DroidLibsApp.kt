package com.streamliners.ui.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.streamliners.base.BaseActivity
import com.streamliners.base.ext.hiltBaseViewModel
import com.streamliners.base.ext.showMessageDialog
import com.streamliners.feature.base_sample.BaseSampleScreen
import com.streamliners.feature.compose.CenterSampleScreen
import com.streamliners.feature.compose.ComposeScreen
import com.streamliners.feature.compose.DrawingPadSampleScreen
import com.streamliners.feature.compose.search_bar.SearchBarSampleScreen
import com.streamliners.feature.compose.text_input_layout.TextInputLayoutScreen
import com.streamliners.feature.home.HomeScreen
import com.streamliners.feature.pickers_sample.PickersSampleScreen
import com.streamliners.feature.task_state_sample.TaskStateSampleScreen
import com.streamliners.pickers.date.showDatePickerDialog
import com.streamliners.pickers.date.showDateRangePickerDialog
import com.streamliners.pickers.date.showMultipleDatesPickerDialog

@ExperimentalMaterial3Api
@Composable
fun BaseActivity.DroidLibsApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.PickersSampleScreen.route
    ) {

        composable(Route.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(Route.TaskStateSampleScreen.route) {
            TaskStateSampleScreen(
                navController = navController,
                viewModel = hiltBaseViewModel()
            )
        }

        composable(Route.BaseSampleScreen.route) {
            BaseSampleScreen(
                navController = navController,
                viewModel = hiltBaseViewModel()
            )
        }

        composable(Route.ComposeScreen.route) {
            ComposeScreen(navController = navController)
        }

        composable(Route.TextInputLayoutScreen.route) {
            TextInputLayoutScreen(navController = navController)
        }

        composable(Route.SearchBarSampleScreen.route) {
            SearchBarSampleScreen(
                navController = navController,
                viewModel = hiltBaseViewModel()
            )
        }

        composable(Route.CenterSampleScreen.route) {
            CenterSampleScreen(
                navController = navController
            )
        }

        composable(Route.DrawingPadSampleScreen.route) {
            DrawingPadSampleScreen(
                navController = navController
            )
        }

        composable(Route.PickersSampleScreen.route) {
            PickersSampleScreen(
                viewModel = hiltBaseViewModel(),
                navController = navController,
                showDatePicker = ::showDatePickerDialog,
                showMultipleDatesPicker = ::showMultipleDatesPickerDialog,
                showDateRangePicker = ::showDateRangePickerDialog,
                showMessageDialog = ::showMessageDialog
            )
        }
    }
}