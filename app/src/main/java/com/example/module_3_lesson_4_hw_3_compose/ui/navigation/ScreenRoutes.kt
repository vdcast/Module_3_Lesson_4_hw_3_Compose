package com.example.module_3_lesson_4_hw_3_compose.ui.navigation

sealed class ScreenRoutes(val route: String) {
    object ScreenMain : ScreenRoutes("screenMain")
    object ScreenListOfUsers : ScreenRoutes("screenListOfUsers")
    object ScreenProfileOfUser : ScreenRoutes("screenProfileOfUser")
    object ScreenRepositoriesOfUser: ScreenRoutes("screenRepositoriesOfUser")
}
