package com.streamliners.ui.main

sealed class Route(
    val route: String
) {

    data object HomeScreen: Route("home")

    data object ComposeScreen: Route("compose")
    data object TextInputLayoutScreen: Route("compose/textInputLayout")

    data object BaseSampleScreen: Route("base")

}