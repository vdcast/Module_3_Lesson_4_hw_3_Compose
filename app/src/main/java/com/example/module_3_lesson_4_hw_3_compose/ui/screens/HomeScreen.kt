package com.example.module_3_lesson_4_hw_3_compose.ui.screens

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.module_3_lesson_4_hw_3_compose.R
import com.example.module_3_lesson_4_hw_3_compose.ui.AppViewModel
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Purple40
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.White80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    appViewModel: AppViewModel,
    onSearchClicked: () -> Unit
) {
    var countryTextField by remember { mutableStateOf("") }
    var languageTextField by remember { mutableStateOf("") }
    var usernameTextField by remember { mutableStateOf("") }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .clickable { focusManager.clearFocus() },
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier
                .padding(
                    top = dimensionResource(id = R.dimen.padding_xlarge),
                    bottom = dimensionResource(id = R.dimen.padding_medium),
                    start = dimensionResource(id = R.dimen.padding_large),
                    end = dimensionResource(id = R.dimen.padding_large)
                ),
            text = stringResource(id = R.string.text_intro),
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.Monospace,
            color = Color.White
        )
        Column(
            modifier = Modifier
                .padding(bottom = dimensionResource(id = R.dimen.padding_large)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = usernameTextField,
                onValueChange = {
                    usernameTextField = it.lowercase()
                    if (it.isNotEmpty()) {
                        countryTextField = ""
                        languageTextField = ""
                    }
                },
                label = { Text(stringResource(id = R.string.username)) },
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
            Text(
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = dimensionResource(id = R.dimen.padding_large)),
                text = stringResource(id = R.string.or),
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = White80
            )
            OutlinedTextField(
                value = countryTextField,
                onValueChange = {
                    countryTextField = it.lowercase()
                    if (it.isNotEmpty()) {
                        usernameTextField = ""
                    }
                },
                label = { Text(stringResource(id = R.string.country)) },
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
                onValueChange = {
                    languageTextField = it.lowercase()
                    if (it.isNotEmpty()) {
                        usernameTextField = ""
                    }
                },
                label = { Text(stringResource(id = R.string.programming_lanuage)) },
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
                modifier = Modifier.width(dimensionResource(id = R.dimen.button_width)),
                onClick = {

                    if (usernameTextField.equals("") && countryTextField.equals("")) {
                        Toast.makeText(
                            context,
                            R.string.toast_no_username_or_country,
                            Toast.LENGTH_SHORT
                        ).apply {
                            setGravity(Gravity.CENTER, 0, 0)
                            show()
                        }
                    } else {
                        if (!usernameTextField.equals("")) {
                            appViewModel.searchByUsername(
                                query = usernameTextField,
                                usernameTextField
                            )
                            onSearchClicked()
                        }
                        if (!countryTextField.equals("")) {
                            if (!languageTextField.equals("")) {
                                val query = "location:$countryTextField language:$languageTextField"
                                appViewModel.searchByCountryAndLanguage(
                                    query = query,
                                    textfieldOne = countryTextField,
                                    textfieldTwo = languageTextField
                                )
                                onSearchClicked()
                            } else {
                                Toast.makeText(
                                    context,
                                    R.string.toast_no_language,
                                    Toast.LENGTH_SHORT
                                ).apply {
                                    setGravity(Gravity.CENTER, 0, 0)
                                    show()
                                }
                            }
                        }
                    }


                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Purple40,
                    contentColor = Color.White,
                )
            ) {
                Text(text = stringResource(id = R.string.button_search))
            }
        }
    }
}