package com.example.module_3_lesson_4_hw_3_compose

import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Items
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.ResponseMain
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface API {
    @GET("search/users?q=location:ukraine+language:java")
    fun getItemsOfUsers(): Call<ResponseMain>

    @GET("search/users")
    fun search(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Call<Items>
}