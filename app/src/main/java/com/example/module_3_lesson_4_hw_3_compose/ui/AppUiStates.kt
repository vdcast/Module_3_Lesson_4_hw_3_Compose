package com.example.module_3_lesson_4_hw_3_compose.ui

import com.example.module_3_lesson_4_hw_3_compose.Items
import com.example.module_3_lesson_4_hw_3_compose.Repositories

data class SearchUiState(
    val itemsOfUsers: List<Items> = listOf(),
    val textfieldOne: String = "",
    val textfieldTwo: String = ""
)
data class ProfileUiState(
    val currentUser: Items = Items(
        "1", 1, "1", "1", "1", "1", "1",
        "1", "1", "1",  "1", "1",
        "1", "1", "1", "1", "1", false,
        1
    )
)
data class ReposUiState(
    val repositoriesOfUser: List<Repositories> = listOf(),
    val loginOfUser: String = ""
)