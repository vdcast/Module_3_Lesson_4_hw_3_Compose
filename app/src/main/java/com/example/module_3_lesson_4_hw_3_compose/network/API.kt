package com.example.module_3_lesson_4_hw_3_compose.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("search/users")
    fun searchCountryLanguage(
        @Query("q") query: String
    ): Call<ResponseMain>
    @GET("users/{user}/repos")
    fun getRepositories(
        @Path("user") user: String
    ): Call<List<Repositories>>
    @GET("search/users")
    fun searchByUsername(
        @Query("q") query: String
    ): Call<ResponseMain>
}