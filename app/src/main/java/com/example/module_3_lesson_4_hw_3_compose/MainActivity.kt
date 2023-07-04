package com.example.module_3_lesson_4_hw_3_compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Module_3_Lesson_4_hw_3_ComposeTheme
import com.example.module_3_lesson_4_hw_3_compose.ui.theme.Purple40


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Module_3_Lesson_4_hw_3_ComposeTheme {
                MyApp()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp() {
    var countryTextField by remember { mutableStateOf("") }
    var languageTextField by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    val focusManager = LocalFocusManager.current

    Image(
        painter = painterResource(id = R.drawable.github_cellphone),
        contentDescription = "Image background",
        contentScale = ContentScale.FillBounds,
        modifier = Modifier
            .graphicsLayer { alpha = 0.9f }
    )

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
                textStyle = TextStyle(color = Purple40)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_small)))
            OutlinedTextField(
                value = languageTextField,
                onValueChange = { languageTextField = it },
                label = { Text("Language") },
                textStyle = TextStyle(color = Color.White)
            )
            Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.padding_medium)))
            OutlinedButton(
                onClick = {

                },
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = Purple40,
                    contentColor = Color.White,
                ),
                border = BorderStroke(0.dp, Color.Transparent)
            ) {
                Text(text = stringResource(id = R.string.button_search))
            }
        }



    }
}

