package com.example.module_3_lesson_4_hw_3_compose.ui

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.module_3_lesson_4_hw_3_compose.API
import com.example.module_3_lesson_4_hw_3_compose.R
import com.example.module_3_lesson_4_hw_3_compose.ResponseMain
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Purple40
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun MyApp(
    retrofit: API
) {
    val navController = rememberNavController()
    Image(
        painter = painterResource(id = R.drawable.github_cellphone),
        contentDescription = "Image background",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .fillMaxSize()
//            .graphicsLayer { alpha = 0.9f }

    )

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.ScreenMain.route
    ) {
        composable(ScreenRoutes.ScreenMain.route) {
            ScreenMain(
                retrofit = retrofit,
                onSearchClicked = {
                    navController.navigate(ScreenRoutes.ScreenListOfUsers.route)
                }
            )
        }
        composable(
            route = ScreenRoutes.ScreenListOfUsers.route
        ) {
            ScreenListOfUsers()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMain(
    retrofit: API,
    onSearchClicked: () -> Unit,
    appViewModel: AppViewModel = viewModel()
) {
    var countryTextField by remember { mutableStateOf("") }
    var languageTextField by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current



    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = stringResource(id = R.string.text_intro),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            color = Color.White,
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.padding_xlarge),
                    bottom = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_large),
                    end = dimensionResource(id = R.dimen.padding_large)
                )
                .align(Alignment.TopCenter)
        )


        Column(
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.padding_large))
                .align(Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = countryTextField,
                onValueChange = { countryTextField = it },
                label = { Text("Country") },
                textStyle = TextStyle(color = Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            OutlinedTextField(
                value = languageTextField,
                onValueChange = { languageTextField = it },
                label = { Text("Language") },
                textStyle = TextStyle(color = Color.White),
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                    }
                ),
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Button(
                onClick = {
                    onSearchClicked()
                    Log.d("MYLOG", "Country: $countryTextField, Language: $languageTextField")

                    val country = countryTextField
                    val language = languageTextField

                    val query = "location:$country language:$language"

                    Log.d("MYLOG", "query: $query")

                    appViewModel.searchUsers(query)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White,
                ),
                modifier = Modifier.width(dimensionResource(id = R.dimen.button_width))
            ) {
                Text(text = stringResource(id = R.string.button_search))
            }
            Button(
                onClick = {
                    val query = "location:ukraine+language:kotlin"
                    retrofit.test("location:ukraine language:kotlin").enqueue(object : Callback<ResponseMain> {
                        override fun onResponse(
                            call: Call<ResponseMain>,
                            response: Response<ResponseMain>
                        ) {
                            if (response.isSuccessful) {
                                val itemsFromGithub = response.body()?.items
                                val idsList = itemsFromGithub?.map { it.id.toString() } ?: emptyList()
                                val idsArray = idsList.toTypedArray()

                                val totalCount = response.body()?.total_count.toString()

                                if (idsArray.isNotEmpty()) {
                                    Log.d("MYLOG", "2 | ${idsArray[0]}, ${idsArray[1]}, ${idsArray[idsArray.size - 1]}")
                                    Log.d("MYLOG", "2 | ${idsArray.size}")
                                    Log.d("MYLOG", "2 | Total count: $totalCount")
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
            ) {
                Text(text = "TEST")
            }
        }


    }
}

@Composable
fun ScreenListOfUsers() {

    val itemsKek = arrayOf("kek1", "kek2", "kek3")

    Column() {

        LazyColumn() {
            itemsIndexed(itemsKek) { index, item ->
                Text(
                    text = itemsKek[index],
                    color = Color.White
                )
                Text(
                    text = item,
                    color = Color.White
                )
            }
        }
    }

}