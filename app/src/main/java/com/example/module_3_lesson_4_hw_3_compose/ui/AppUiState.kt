package com.example.module_3_lesson_4_hw_3_compose.ui

import com.example.module_3_lesson_4_hw_3_compose.Items

data class AppUiState(
    val itemsOfUsers: List<Items> = listOf()
)