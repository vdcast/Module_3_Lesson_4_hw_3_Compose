package com.example.module_3_lesson_4_hw_3_compose

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface API {
    @GET("search/users")
    fun search(
        @Query("q") query: String
    ): Call<ResponseMain>
    @GET("users/{user}/repos")
    fun getRepositories(
        @Path("user") user: String
    ): Call<List<Repositories>>
    @GET("users/{user}/repos")
    fun testGetRepositories(
        @Path("user") user: String
    ): Call<List<Repositories>>
}