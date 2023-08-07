package com.example.module_3_lesson_4_hw_3_compose.ui

import com.example.module_3_lesson_4_hw_3_compose.network.Items
import com.example.module_3_lesson_4_hw_3_compose.network.Repositories

data class SearchUiState(
    val itemsOfUsers: List<Items> = listOf(),
    val textfieldOne: String = "",
    val textfieldTwo: String = "",
    val usernameSearch: Boolean = true
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