package com.example.module_3_lesson_4_hw_3_compose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Module_3_Lesson_4_hw_3_ComposeTheme
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Purple40
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.ResponseMain
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var retrofit = RetrofitClient.getClient("https://api.github.com/")
            .create(API::class.java)



        setContent {
            Module_3_Lesson_4_hw_3_ComposeTheme {
                MyApp(retrofit)
//                ScreenMain(
//                    retrofit,
//                    onSearchClicked = {
//                        Log.d("MYLOG", "CLICKED BRO :D")
//                    }
//                )
            }
        }
    }
}


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
                    navController.navigate("${ScreenRoutes.ScreenListOfUsers.route}/$it")
                }
            )
        }
        composable(
            route = "${ScreenRoutes.ScreenListOfUsers.route}/{my_param}",
            arguments = listOf(
                navArgument("my_param") {
                    type = NavType.StringType
                }
            )
        ) {
            val param = it.arguments?.getString("my_param") ?: ""
            ScreenListOfUsers(param = param)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMain(
    retrofit: API,
    onSearchClicked: (String) -> Unit,
) {
    var countryTextField by remember { mutableStateOf("") }
    var languageTextField by remember { mutableStateOf("") }
    var searchText by remember { mutableStateOf("kek") }

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

        Text(
            text = searchText,
            color = Color.White,
            modifier = Modifier.align(Alignment.Center)
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
                    onSearchClicked("SEARCH TEXT HAHAHA")
                    Log.d("MYLOG", "Country: $countryTextField, Language: $languageTextField")
//                    retrofit.getItemsOfUsers().enqueue(object : Callback<ResponseMain> {
//                        override fun onResponse(
//                            call: Call<ResponseMain>,
//                            response: Response<ResponseMain>
//                        ) {
//                            val result = response.body()?.total_count.toString()
//                            Log.d("MYLOG", "Found users from GitHub: $result")
//                        }
//
//                        override fun onFailure(call: Call<ResponseMain>, t: Throwable) {
//                            Log.d("MYLOG", "Some error in query. 1")
//                        }
//
//                    })
                    retrofit.getItemsOfUsers().enqueue(object : Callback<ResponseMain> {
                        override fun onResponse(
                            call: Call<ResponseMain>,
                            response: Response<ResponseMain>
                        ) {
                            val result = response.body()?.items
                            Log.d("MYLOG", "Found some: $result")
                        }

                        override fun onFailure(call: Call<ResponseMain>, t: Throwable) {
                            Log.d("MYLOG", "Some error in query. 2")
                        }


                    })
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White,
                ),
                modifier = Modifier.width(dimensionResource(id = R.dimen.button_width))
            ) {
                Text(text = stringResource(id = R.string.button_search))
            }
        }


    }
}

@Composable
fun ScreenListOfUsers(param: String) {
    Text(text = param, color = Color.White)
}
