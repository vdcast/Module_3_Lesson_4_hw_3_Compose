package com.example.module_3_lesson_4_hw_3_compose.ui.screens

import android.view.Gravity
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import com.example.module_3_lesson_4_hw_3_compose.R
import com.example.module_3_lesson_4_hw_3_compose.data.Countries
import com.example.module_3_lesson_4_hw_3_compose.data.ProgrammingLanguages
import com.example.module_3_lesson_4_hw_3_compose.ui.AppViewModel
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Purple40
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.White80

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    appViewModel: AppViewModel,
    onSearchClicked: () -> Unit
) {
    var usernameTextField by remember { mutableStateOf("") }
    var countryTextField by remember { mutableStateOf(TextFieldValue("")) }
    var languageTextField by remember { mutableStateOf(TextFieldValue("")) }

    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

//    val countries = listOf("Ukraine", "Cyprus", "India", "United States")
    val countries = Countries.values().map { it.displayName }
    var filteredCountries by remember { mutableStateOf(listOf<String>()) }
    var showDropdownCountries by remember { mutableStateOf(false) }

//    val languages = listOf("Kotlin", "Java", "JavaScript")
    val languages = ProgrammingLanguages.values().map { it.displayName }
    var filteredLanguages by remember { mutableStateOf(listOf<String>()) }
    var showDropdownLanguages by remember { mutableStateOf(false) }

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
                modifier = Modifier.fillMaxWidth(0.725f),
                value = usernameTextField,
                onValueChange = {
                    usernameTextField = it.lowercase()
                    if (it.isNotEmpty()) {
                        countryTextField = TextFieldValue("")
                        languageTextField = TextFieldValue("")
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
            Box() {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.725f),
                    value = countryTextField,
                    onValueChange = {
                        countryTextField = it
                        if (it.text.isNotEmpty()) {
                            usernameTextField = ""
                            filteredCountries = countries.filter { country ->
                                country.startsWith(it.text, ignoreCase = true)
                            }
                            showDropdownCountries = filteredCountries.isNotEmpty()
                        } else {
                            showDropdownCountries = false
                        }
                    },
                    label = { Text(stringResource(id = R.string.country)) },
                    textStyle = TextStyle(color = Color.White),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                )
                if (showDropdownCountries) {
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = { showDropdownCountries = false },
                        properties = PopupProperties(focusable = false)
                    ) {
                        filteredCountries.forEach { country ->
                            DropdownMenuItem(
                                text = { Text(text = country) },
                                onClick = {
                                    countryTextField = TextFieldValue(country, TextRange(country.length))
                                    showDropdownCountries = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            Box() {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(0.725f),
                    value = languageTextField,
                    onValueChange = {
                        languageTextField = it
                        if (it.text.isNotEmpty()) {
                            usernameTextField = ""
                             filteredLanguages = languages.filter { language ->
                                language.startsWith(it.text, ignoreCase = true)
                            }
                            showDropdownLanguages = filteredLanguages.isNotEmpty()
                        } else {
                            showDropdownLanguages = false
                        }
                    },
                    label = { Text(stringResource(id = R.string.programming_lanuage)) },
                    textStyle = TextStyle(color = Color.White),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done,
                        capitalization = KeyboardCapitalization.Sentences
                    ),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            focusManager.clearFocus()
                        }
                    ),
                )
                if (showDropdownLanguages) {
                    DropdownMenu(
                        expanded = true,
                        onDismissRequest = { showDropdownLanguages = false },
                        properties = PopupProperties(focusable = false)
                    ) {
                        filteredLanguages.forEach { language ->
                            DropdownMenuItem(
                                text = { Text(text = language) },
                                onClick = {
                                    languageTextField = TextFieldValue(language, TextRange(language.length))
                                    showDropdownLanguages = false
                                }
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            Button(
                modifier = Modifier.fillMaxWidth(0.35f),
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
                                val query = "location:${countryTextField.text} language:${languageTextField.text}"
                                appViewModel.searchByCountryAndLanguage(
                                    query = query,
                                    textfieldOne = countryTextField.text,
                                    textfieldTwo = languageTextField.text
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