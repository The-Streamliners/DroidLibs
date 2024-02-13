package com.streamliners.ui.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.streamliners.base.BaseActivity
import com.streamliners.base.ext.hiltBaseViewModel
import com.streamliners.feature.base_sample.BaseSampleScreen
import com.streamliners.feature.compose.ComposeScreen
import com.streamliners.feature.compose.text_input_layout.TextInputLayoutScreen
import com.streamliners.feature.home.HomeScreen
import com.streamliners.feature.task_state_sample.TaskStateSampleScreen

@ExperimentalMaterial3Api
@Composable
fun BaseActivity.DroidLibsApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.TextInputLayoutScreen.route
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
    }
}