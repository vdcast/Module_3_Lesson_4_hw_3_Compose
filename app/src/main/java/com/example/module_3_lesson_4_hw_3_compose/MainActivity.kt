package com.example.module_3_lesson_4_hw_3_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.module_3_lesson_4_hw_3_compose.ui.GitHubSearch
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Module_3_Lesson_4_hw_3_ComposeTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Module_3_Lesson_4_hw_3_ComposeTheme {
                GitHubSearch()
            }
        }
    }
}



