package com.example.module_3_lesson_4_hw_3_compose.ui.screens

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.module_3_lesson_4_hw_3_compose.R
import com.example.module_3_lesson_4_hw_3_compose.ui.AppViewModel
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Black10


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListOfUsersScreen(
    appViewModel: AppViewModel,
    onItemClicked: () -> Unit
) {
    val searchUiState by appViewModel.searchUiState.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        item {
            Text(
                modifier = Modifier
                    .padding(
                        all = dimensionResource(id = R.dimen.padding_medium)
                    ),
                text = stringResource(
                    id = when {
                        searchUiState.usernameSearch -> R.string.text_search_list_username
                        else -> R.string.text_search_list_country_language
                    },
                    searchUiState.textfieldOne,
                    searchUiState.textfieldTwo
                ),
                textAlign = TextAlign.Center,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Monospace,
                color = Color.White
            )
        }
        if (searchUiState.itemsOfUsers.isEmpty()) {
            item {
                CircularProgressIndicator()
            }
        } else {
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
}