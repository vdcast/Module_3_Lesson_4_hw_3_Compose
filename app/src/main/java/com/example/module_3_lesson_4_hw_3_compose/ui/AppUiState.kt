package com.example.module_3_lesson_4_hw_3_compose.ui

import com.example.module_3_lesson_4_hw_3_compose.Items
import com.example.module_3_lesson_4_hw_3_compose.Repositories

data class AppUiState(
    val itemsOfUsers: List<Items> = listOf(),
    val currentUser: Items = Items(
        "1", 1, "1", "1", "1", "1", "1",
        "1", "1", "1",  "1", "1",
        "1", "1", "1", "1", "1", false,
        1
    ),
    val repositoriesOfUser: List<Repositories> = listOf()
)