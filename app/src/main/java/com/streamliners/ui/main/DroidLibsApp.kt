package com.streamliners.ui.main

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.streamliners.feature.compose.ComposeScreen
import com.streamliners.feature.compose.text_input_layout.TextInputLayoutScreen
import com.streamliners.feature.home.HomeScreen

@ExperimentalMaterial3Api
@Composable
fun DroidLibsApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Route.HomeScreen.route
    ) {

        composable(Route.HomeScreen.route) {
            HomeScreen(navController = navController)
        }

        composable(Route.ComposeScreen.route) {
            ComposeScreen(navController = navController)
        }

        composable(Route.TextInputLayoutScreen.route) {
            TextInputLayoutScreen(navController = navController)
        }
    }
}