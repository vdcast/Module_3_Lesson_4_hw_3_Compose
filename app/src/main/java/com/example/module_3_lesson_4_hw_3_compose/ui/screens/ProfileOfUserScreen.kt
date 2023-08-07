package com.example.module_3_lesson_4_hw_3_compose.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.module_3_lesson_4_hw_3_compose.R
import com.example.module_3_lesson_4_hw_3_compose.ui.AppViewModel
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Black10
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Purple40


@Composable
fun ProfileOfUserScreen(
    appViewModel: AppViewModel,
    onRepositoriesClicked: () -> Unit
) {
    val profileUiState by appViewModel.profileUiState.collectAsState()
    val uriHandler = LocalUriHandler.current

    if (profileUiState.currentUser.login == "1") {
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
                        .padding(top = dimensionResource(id = R.dimen.padding_medium))
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

}