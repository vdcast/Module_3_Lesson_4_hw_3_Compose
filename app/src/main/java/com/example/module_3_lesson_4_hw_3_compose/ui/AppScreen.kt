package com.example.module_3_lesson_4_hw_3_compose.ui

import android.util.Log
import android.view.Gravity
import android.view.WindowId.FocusObserver
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.module_3_lesson_4_hw_3_compose.R
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Black10
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Module_3_Lesson_4_hw_3_ComposeTheme
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Pink40
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Pink50
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Purple40

@Composable
fun MyApp(
    appViewModel: AppViewModel = viewModel()
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
                appViewModel = appViewModel,
                onSearchClicked = {
                    navController.navigate(ScreenRoutes.ScreenListOfUsers.route)
                }
            )
        }
        composable(
            route = ScreenRoutes.ScreenListOfUsers.route
        ) {
            ScreenListOfUsers(
                appViewModel = appViewModel,
                onItemClicked = {

                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenMain(
    appViewModel: AppViewModel,
    onSearchClicked: () -> Unit
) {
    var countryTextField by remember { mutableStateOf("") }
    var languageTextField by remember { mutableStateOf("") }

    val focusManager = LocalFocusManager.current

    val context = LocalContext.current

    val appUiState by appViewModel.uiState.collectAsState()
    var test by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() }
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
            Text(
                text = test,
                color = Color.White
            )

            OutlinedTextField(
                value = countryTextField,
                onValueChange = { countryTextField = it.lowercase() },
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
                onValueChange = { languageTextField = it.lowercase() },
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
                    if (!countryTextField.equals("")) {
                        if (!languageTextField.equals("")) {
                            val query = "location:$countryTextField language:$languageTextField"
                            appViewModel.searchUsers(query)
                            onSearchClicked()
                        } else {
                            Toast.makeText(context, R.string.toast_no_language, Toast.LENGTH_SHORT)
                                .apply {
                                    setGravity(Gravity.CENTER, 0, 0)
                                    show()
                                }
                        }
                    } else {
                        Toast.makeText(context, R.string.toast_no_country, Toast.LENGTH_SHORT)
                            .apply {
                                setGravity(Gravity.CENTER, 0, 0)
                                show()
                            }
                    }
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
                    val query = "location:cyprus language:kotlin"
                    appViewModel.searchUsers(query)
                }
            ) {
                Text(text = "TEST")
            }
            Button(
                onClick = {
                    test = appUiState.itemsOfUsers[0].id.toString()
                }
            ) {
                Text(text = "TEST 2")
            }
        }


    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenListOfUsers(
    appViewModel: AppViewModel,
    onItemClicked: () -> Unit
) {

    val appUiState by appViewModel.uiState.collectAsState()

    val itemsTest = appUiState.itemsOfUsers

    Log.d("MYLOG", "ScreenListOfUsers: ${itemsTest.toString()}")



    LazyColumn() {
        itemsIndexed(appUiState.itemsOfUsers) { index, item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        all = dimensionResource(id = R.dimen.padding_xsmall)
                    )
                    .height(dimensionResource(id = R.dimen.padding_xxlarge)),
                shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium)),
                elevation = CardDefaults.cardElevation(
                    dimensionResource(id = R.dimen.padding_xsmall)
                ),
                colors = CardDefaults.cardColors(Black10),
                onClick = {
                    onItemClicked()
                }
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = item.avatar_url,
                        contentDescription = "avatar",
                        modifier = Modifier
                            .padding(start = dimensionResource(id = R.dimen.padding_small))
                            .clip(CircleShape)
                            .size(dimensionResource(id = R.dimen.padding_xlarge)),
                    )
                    Text(
                        text = item.login,
                        color = Color.White,
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = dimensionResource(id = R.dimen.padding_medium))
                    )
                    Text(
                        text = item.id.toString(),
                        color = Color.White,
                        modifier = Modifier
                            .weight(0.7f)
                    )
                }
            }
        }
    }
}


@Composable
fun ScreenProfileOfUser(
    appViewModel: AppViewModel
) {

}