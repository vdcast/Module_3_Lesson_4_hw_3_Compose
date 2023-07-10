package com.example.module_3_lesson_4_hw_3_compose.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.module_3_lesson_4_hw_3_compose.API
import com.example.module_3_lesson_4_hw_3_compose.ResponseMain
import com.example.module_3_lesson_4_hw_3_compose.Repositories
import com.example.module_3_lesson_4_hw_3_compose.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppViewModel: ViewModel() {
    private val _searchUiState = MutableStateFlow(SearchUiState())
    val searchUiState: StateFlow<SearchUiState> = _searchUiState.asStateFlow()

    private val _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState: StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    private val _reposUiState = MutableStateFlow(ReposUiState())
    val reposUiState: StateFlow<ReposUiState> = _reposUiState.asStateFlow()

    private val retrofit = RetrofitClient.getClient("https://api.github.com/")
        .create(API::class.java)

    fun searchUsers(query: String) {
        _searchUiState.value = SearchUiState()

        retrofit.search(query).enqueue(object : Callback<ResponseMain> {
            override fun onResponse(
                call: Call<ResponseMain>,
                response: Response<ResponseMain>
            ) {
                if (response.isSuccessful) {
                    val itemsFromGithub = response.body()?.items
                    Log.d("MYLOG", itemsFromGithub.toString())
                    Log.d("MYLOG", "21 | ${_searchUiState.value.toString()}")
                    if (itemsFromGithub == null) {
                        Log.d("MYLOG", "items = null")
                    } else {
                        _searchUiState.value = SearchUiState(itemsOfUsers = itemsFromGithub)
                        Log.d("MYLOG", "22 | ${_searchUiState.value.toString()}")
                    }
                    val idsList = itemsFromGithub?.map { it.id.toString() } ?: emptyList()
                    val idsArray = idsList.toTypedArray()
                    val totalCount = response.body()?.total_count.toString()
                    if (idsArray.isNotEmpty()) {
                        Log.d("MYLOG", "${idsArray[0]}, ${idsArray[1]}, ${idsArray[idsArray.size - 1]}")
                        Log.d("MYLOG", "${idsArray.size}")
                        Log.d("MYLOG", "Total count: $totalCount")
                    }
                } else {
                    Log.d("MYLOG", "Response not successful. Code: ${response.code()}, Message: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseMain>, t: Throwable) {
                Log.d("MYLOG", "Some error in query. 1 | Error: ${t.message}")
            }
        })
    }

    fun chosenUser(query: String) {
        _profileUiState.value = ProfileUiState()

        retrofit.search(query).enqueue(object : Callback<ResponseMain> {
            override fun onResponse(
                call: Call<ResponseMain>,
                response: Response<ResponseMain>
            ) {
                if (response.isSuccessful) {
                    val itemsFromGithub = response.body()?.items
                    if (itemsFromGithub == null) {
                        Log.d("MYLOG", "items = null")
                    } else {
                        val currentUser = itemsFromGithub[0]
                        _profileUiState.update { currentState ->
                            currentState.copy(currentUser = currentUser)
                        }
                        Log.d("MYLOG", "33 | ${_profileUiState.value.toString()}")
                    }
                } else {
                    Log.d("MYLOG", "Response not successful. Code: ${response.code()}, Message: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<ResponseMain>, t: Throwable) {
                Log.d("MYLOG", "Some error in query. 2 | Error: ${t.message}")
            }

        })

    }

    fun usersRepositories(user: String) {
        _reposUiState.value = ReposUiState()

        retrofit.getRepositories(user).enqueue(object : Callback<List<Repositories>> {
            override fun onResponse(
                call: Call<List<Repositories>>,
                response: Response<List<Repositories>>
            ) {
                if (response.isSuccessful) {
                    val repositoriesOfUser = response.body()
                    if (repositoriesOfUser == null) {
                        Log.d("MYLOG", "repositories = null")
                    } else {
                        _reposUiState.update { currentState ->
                            currentState.copy(repositoriesOfUser = repositoriesOfUser)
                        }
                        Log.d("MYLOG", "55 | ${_reposUiState.value.repositoriesOfUser}")
                    }
                } else {
                    Log.d("MYLOG", "Response not successful. Code: ${response.code()}, Message: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<Repositories>>, t: Throwable) {
                Log.d("MYLOG", "Some error in query. 3 | Error: ${t.message}")
            }
        })
    }

    fun testUsersRepositories(user: String) {
        retrofit.testGetRepositories(user).enqueue(object : Callback<List<Repositories>> {
            override fun onResponse(
                call: Call<List<Repositories>>,
                response: Response<List<Repositories>>
            ) {
                if (response.isSuccessful) {
                    val repositoriesOfUser = response.body()
                    if (repositoriesOfUser == null) {
                        Log.d("MYLOG", "repositories = null")
                    } else {
                        _reposUiState.update { currentState ->
                            currentState.copy(repositoriesOfUser = repositoriesOfUser)
                        }
                        Log.d("MYLOG", "55 | ${_reposUiState.value.repositoriesOfUser}")
                    }
                } else {
                    Log.d("MYLOG", "Response not successful. Code: ${response.code()}, Message: ${response.message()}")
                }
            }
            override fun onFailure(call: Call<List<Repositories>>, t: Throwable) {
                Log.d("MYLOG", "Some error in query. 4 | Error: ${t.message}")
            }
        })
    }
}