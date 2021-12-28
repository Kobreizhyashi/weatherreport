package com.kth.weatherapp.ui.features.weather

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.kth.weatherapp.R
import com.kth.weatherapp.ui.component.ProgressBar
import com.kth.weatherapp.ui.theme.WeatherAppTheme

// TODO: Should be DI component
@ExperimentalAnimationApi
class WeatherActivity : ComponentActivity() {

    // TODO: Should be injected
    val viewModel: WeatherActivityViewModel = WeatherActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchData()
        setContent {
            WeatherAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Scaffold(topBar = {
                        TopAppBar(
                            title = { Text(getString(R.string.report)) },
                            navigationIcon = {
                                IconButton(
                                    onClick = { onBackPressed() }) {
                                    Icon(
                                        imageVector = Icons.Filled.ArrowBack,
                                        contentDescription = "Back to home page"
                                    )
                                }
                            },
                            contentColor = MaterialTheme.colors.background
                        )
                    }) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Bottom,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BuildProgressBar()
                        }
                    }
                }
            }
        }
    }


    @Composable
    fun BuildProgressBar() {
        val progressValue by viewModel.progressValue.collectAsState()
        val timerOver by viewModel.timerOver.collectAsState()
        if (!timerOver) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                ProgressBar(value = progressValue)
                Text(text = "${progressValue.toInt() * 100}%")

            }
        }
        // TODO Gotta find a better way to choose between those 2 according to [timerOver] value
        AnimatedVisibility(visible = timerOver) {
            Button(onClick = { viewModel.fetchData() }) {
                Text(getString(R.string.report_button))
            }
        }

    }
}
