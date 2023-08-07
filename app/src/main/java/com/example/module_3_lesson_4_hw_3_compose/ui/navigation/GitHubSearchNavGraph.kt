package com.example.module_3_lesson_4_hw_3_compose.ui.navigation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.module_3_lesson_4_hw_3_compose.R
import com.example.module_3_lesson_4_hw_3_compose.ui.AppViewModel
import com.example.module_3_lesson_4_hw_3_compose.ui.screens.HomeScreen
import com.example.module_3_lesson_4_hw_3_compose.ui.screens.ListOfUsersScreen
import com.example.module_3_lesson_4_hw_3_compose.ui.screens.ProfileOfUserScreen
import com.example.module_3_lesson_4_hw_3_compose.ui.screens.RepositoriesOfUserScreen

@Composable
fun GitHubSearchNavHost(
    navController: NavHostController,
    appViewModel: AppViewModel
) {
    Image(
        painter = painterResource(id = R.drawable.github_cellphone),
        contentDescription = "Image background",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .fillMaxSize()
    )

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.ScreenMain.route
    ) {
        composable(ScreenRoutes.ScreenMain.route) {
            HomeScreen(
                appViewModel = appViewModel,
                onSearchClicked = {
                    navController.navigate(ScreenRoutes.ScreenListOfUsers.route)
                }
            )
        }
        composable(ScreenRoutes.ScreenListOfUsers.route) {
            ListOfUsersScreen(
                appViewModel = appViewModel,
                onItemClicked = {
                    navController.navigate(ScreenRoutes.ScreenProfileOfUser.route)
                }
            )
        }
        composable(ScreenRoutes.ScreenProfileOfUser.route) {
            ProfileOfUserScreen(
                appViewModel = appViewModel,
                onRepositoriesClicked = {
                    navController.navigate(ScreenRoutes.ScreenRepositoriesOfUser.route)
                }
            )
        }
        composable(ScreenRoutes.ScreenRepositoriesOfUser.route) {
            RepositoriesOfUserScreen(
                appViewModel = appViewModel
            )
        }
    }
}