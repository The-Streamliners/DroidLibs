package com.streamliners.ui.main

sealed class Route(
    val route: String
) {

    object HomeScreen: Route("home")

    object ComposeScreen: Route("compose")
    object TextInputLayoutScreen: Route("compose/textInputLayout")

}