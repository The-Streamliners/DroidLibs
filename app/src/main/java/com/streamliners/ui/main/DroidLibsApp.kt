package com.streamliners.ui.main

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.streamliners.ui.main.Route.*

@Composable
fun DroidLibsApp() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeScreen.route
    ) {

        composable(HomeScreen.route) {

        }

        composable(ComposeScreen.route) {

        }
    }
}