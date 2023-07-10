package com.example.module_3_lesson_4_hw_3_compose.ui

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.example.module_3_lesson_4_hw_3_compose.Items
import com.example.module_3_lesson_4_hw_3_compose.R
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Black10
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Pink50
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Purple40
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

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
        composable(ScreenRoutes.ScreenListOfUsers.route) {
            ScreenListOfUsers(
                appViewModel = appViewModel,
                onItemClicked = {
                    navController.navigate(ScreenRoutes.ScreenProfileOfUser.route)
                }
            )
        }
        composable(ScreenRoutes.ScreenProfileOfUser.route) {
            ScreenProfileOfUser(
                appViewModel = appViewModel,
                onRepositoriesClicked = {
                    navController.navigate(ScreenRoutes.ScreenRepositoriesOfUser.route)
                }
            )
        }
        composable(ScreenRoutes.ScreenRepositoriesOfUser.route) {
            ScreenRepositoriesOfUser(
                appViewModel = appViewModel
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

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

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
                            appViewModel.searchUsers(query = query)
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
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScreenListOfUsers(
    appViewModel: AppViewModel,
    onItemClicked: () -> Unit
) {
    val searchUiState by appViewModel.searchUiState.collectAsState()

    LazyColumn() {
        itemsIndexed(searchUiState.itemsOfUsers) { index, item ->
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
                    appViewModel.chosenUser(query = item.login)
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
                            .weight(0.5f)
                    )
                }
            }
        }
    }
}


@Composable
fun ScreenProfileOfUser(
    appViewModel: AppViewModel,
    onRepositoriesClicked: () -> Unit
) {
    val profileUiState by appViewModel.profileUiState.collectAsState()
    val uriHandler = LocalUriHandler.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                all = dimensionResource(id = R.dimen.padding_small)
            ),
        shape = RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium)),
        elevation = CardDefaults.cardElevation(
            dimensionResource(id = R.dimen.padding_xsmall)
        ),
        colors = CardDefaults.cardColors(Black10)
    ) {
        Column(
            modifier = Modifier
                .padding(all = dimensionResource(id = R.dimen.padding_medium))
        ) {

            AsyncImage(
                model = profileUiState.currentUser.avatar_url,
                contentDescription = "imageOnProfileScreen",
                modifier = Modifier
                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.padding_medium)))
                    .align(Alignment.CenterHorizontally)
            )
            Text(
                modifier = Modifier
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_medium)
                    )
                    .align(Alignment.CenterHorizontally),
                text = profileUiState.currentUser.login,
                color = Color.White,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            )
            
            Divider(
                thickness = dimensionResource(id = R.dimen.thickness_divider),
                modifier = Modifier
                    .padding(top = dimensionResource(id = R.dimen.padding_medium))
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_medium)
                    ),
                onClick = {
                    uriHandler.openUri(profileUiState.currentUser.html_url)
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White,
                )
            ) {
                Text(
                    text = stringResource(id = R.string.profileLink),
                    fontSize = 16.sp
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_small),
                        bottom = dimensionResource(id = R.dimen.padding_small)
                    ),
                onClick = {
                    appViewModel.usersRepositories(
                        user = profileUiState.currentUser.login
                    )
                    onRepositoriesClicked()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White,
                )
            ) {
                Text(
                    text = stringResource(id = R.string.button_repositories),
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Composable
fun ScreenRepositoriesOfUser(
    appViewModel: AppViewModel
) {
    val reposUiState by appViewModel.reposUiState.collectAsState()
    val usersRepositories = reposUiState.repositoriesOfUser

    LazyColumn(
        modifier = Modifier.padding(all = 16.dp)
    ) {
        itemsIndexed(usersRepositories) { index, item ->

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    modifier = Modifier.weight(1f),
                    text = item.name,
                    color = Color.White,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                HyperlinkText(
                    modifier = Modifier
                        .weight(0.2f)
                        .padding(start = dimensionResource(id = R.dimen.padding_medium)),
                    fullText = stringResource(id = R.string.open),
                    hyperLinks = mutableMapOf(
                        stringResource(id = R.string.open) to reposUiState.repositoriesOfUser[index].html_url
                    ),
                    linkTextColor = Pink50
                )
            }


        }
    }
}

@Composable
fun HyperlinkText(
    modifier: Modifier = Modifier,
    fullText: String,
    hyperLinks: Map<String, String>,
    textStyle: TextStyle = TextStyle.Default,
    linkTextColor: Color = Color.Blue,
    linkTextFontWeight: FontWeight = FontWeight.Normal,
    linkTextDecoration: TextDecoration = TextDecoration.Underline,
    fontSize: TextUnit = TextUnit.Unspecified
) {
    val annotatedString = buildAnnotatedString {
        append(fullText)

        for ((key, value) in hyperLinks) {

            val startIndex = fullText.indexOf(key)
            val endIndex = startIndex + key.length
            addStyle(
                style = SpanStyle(
                    color = linkTextColor,
                    fontSize = 18.sp,
                    fontWeight = linkTextFontWeight,
                    textDecoration = linkTextDecoration
                ),
                start = startIndex,
                end = endIndex
            )
            addStringAnnotation(
                tag = "URL",
                annotation = value,
                start = startIndex,
                end = endIndex
            )
        }
    }

    val uriHandler = LocalUriHandler.current

    ClickableText(
        modifier = modifier,
        text = annotatedString,
        style = textStyle,
        onClick = {
            annotatedString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        },
    )
}