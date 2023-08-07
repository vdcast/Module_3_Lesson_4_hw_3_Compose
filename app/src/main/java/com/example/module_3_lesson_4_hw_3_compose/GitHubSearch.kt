package com.example.module_3_lesson_4_hw_3_compose.ui

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.module_3_lesson_4_hw_3_compose.ui.navigation.GitHubSearchNavHost

@Composable
fun GitHubSearch(
    appViewModel: AppViewModel = viewModel(),
    navController:NavHostController = rememberNavController()
) {
    GitHubSearchNavHost(
        navController = navController,
        appViewModel = appViewModel
    )
}