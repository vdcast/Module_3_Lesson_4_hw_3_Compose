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

    fun searchByCountryAndLanguage(query: String, textfieldOne: String, textfieldTwo: String) {
        _searchUiState.value = SearchUiState()

        retrofit.searchCountryLanguage(query).enqueue(object : Callback<ResponseMain> {
            override fun onResponse(
                call: Call<ResponseMain>,
                response: Response<ResponseMain>
            ) {
                if (response.isSuccessful) {
                    val itemsFromGithub = response.body()?.items
                    if (itemsFromGithub == null) {
                        Log.d("MYLOG", "items = null")
                    } else {
                        _searchUiState.value = SearchUiState(
                            itemsOfUsers = itemsFromGithub,
                            textfieldOne = textfieldOne,
                            textfieldTwo = textfieldTwo,
                            usernameSearch = false
                        )
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
    fun searchByUsername(query: String, textfieldOne: String) {
        _searchUiState.value = SearchUiState()

        retrofit.searchByUsername(query).enqueue(object : Callback<ResponseMain> {
            override fun onResponse(
                call: Call<ResponseMain>,
                response: Response<ResponseMain>
            ) {
                if (response.isSuccessful) {
                    val itemsFromGithub = response.body()?.items
                    if (itemsFromGithub == null) {
                        Log.d("MYLOG", "items = null")
                    } else {
                        _searchUiState.value = SearchUiState(
                            itemsOfUsers = itemsFromGithub,
                            textfieldOne = textfieldOne,
                            usernameSearch = true
                        )
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

        retrofit.searchCountryLanguage(query).enqueue(object : Callback<ResponseMain> {
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
                            currentState.copy(
                                repositoriesOfUser = repositoriesOfUser,
                                loginOfUser = user
                            )
                        }
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
}