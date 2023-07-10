package com.example.module_3_lesson_4_hw_3_compose.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.module_3_lesson_4_hw_3_compose.API
import com.example.module_3_lesson_4_hw_3_compose.ResponseMain
import com.example.module_3_lesson_4_hw_3_compose.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AppViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(AppUiState())
    val uiState: StateFlow<AppUiState> = _uiState.asStateFlow()

    private val retrofit = RetrofitClient.getClient("https://api.github.com/")
        .create(API::class.java)

    fun searchUsers(query: String) {

        retrofit.search(query).enqueue(object : Callback<ResponseMain> {
            override fun onResponse(
                call: Call<ResponseMain>,
                response: Response<ResponseMain>
            ) {
                if (response.isSuccessful) {
                    val itemsFromGithub = response.body()?.items

                    Log.d("MYLOG", itemsFromGithub.toString())
                    Log.d("MYLOG", "21 | ${_uiState.value.toString()}")

                    if (itemsFromGithub == null) {
                        Log.d("MYLOG", "items = null")
                    } else {
                        _uiState.value = AppUiState(itemsOfUsers = itemsFromGithub)
                        Log.d("MYLOG", "22 | ${_uiState.value.toString()}")
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
                Log.d("MYLOG", "Some error in query. 1")
            }

        })

    }

    fun chosenUser(query: String) {

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
//                        _uiState.value = AppUiState(currentUser = currentUser)
                        _uiState.update { currentState ->
                            currentState.copy(currentUser = currentUser)
                        }
                        Log.d("MYLOG", "22 | ${_uiState.value.toString()}")
                    }
                } else {
                    Log.d("MYLOG", "Response not successful. Code: ${response.code()}, Message: ${response.message()}")
                }

            }

            override fun onFailure(call: Call<ResponseMain>, t: Throwable) {
                Log.d("MYLOG", "Some error in query. 1")
            }

        })

    }

}