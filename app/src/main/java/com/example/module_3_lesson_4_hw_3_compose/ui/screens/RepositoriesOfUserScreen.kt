package com.example.module_3_lesson_4_hw_3_compose.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.module_3_lesson_4_hw_3_compose.R
import com.example.module_3_lesson_4_hw_3_compose.ui.AppViewModel
import com.example.module_3_lesson_4_hw_3_compose.ui.HyperlinkText
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Black10
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Pink50


@Composable
fun RepositoriesOfUserScreen(
    appViewModel: AppViewModel
) {
    val reposUiState by appViewModel.reposUiState.collectAsState()
    val usersRepositories = reposUiState.repositoriesOfUser

    if (reposUiState.loginOfUser == "") {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_medium))
            )
        }
    } else {
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(all = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                item {
                    Text(
                        modifier = Modifier
                            .padding(
                                start = dimensionResource(id = R.dimen.padding_medium),
                                end = dimensionResource(id = R.dimen.padding_medium),
                                top = dimensionResource(id = R.dimen.padding_small),
                                bottom = dimensionResource(id = R.dimen.padding_medium),
                            ),
                        text = stringResource(
                            id = R.string.text_repositories_of_user,
                            reposUiState.loginOfUser
                        ),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
                if (usersRepositories.size == 0) {
                    item {
                        Text(
                            modifier = Modifier
                                .padding(
                                    start = dimensionResource(id = R.dimen.padding_medium),
                                    end = dimensionResource(id = R.dimen.padding_medium),
                                    top = dimensionResource(id = R.dimen.padding_small),
                                    bottom = dimensionResource(id = R.dimen.padding_medium),
                                ),
                            text = stringResource(
                                id = R.string.repositories_is_empty
                            ),
                            textAlign = TextAlign.Start,
                            color = Color.White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
                itemsIndexed(usersRepositories) { index, item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                horizontal = dimensionResource(id = R.dimen.padding_xsmall),
                                vertical = dimensionResource(id = R.dimen.padding_xsmall)
                            ),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.weight(1f),
                            text = item.name,
                            color = Color.White,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Spacer(
                            modifier = Modifier
                                .width(dimensionResource(id = R.dimen.padding_small))
                        )
                        HyperlinkText(
                            modifier = Modifier
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
    }


}