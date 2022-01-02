package com.kth.weatherapp.ui.features.weather

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.kth.weatherapp.R
import com.kth.weatherapp.ui.component.ProgressBar
import com.kth.weatherapp.ui.component.TableCell
import com.kth.weatherapp.ui.theme.WeatherAppTheme
import com.kth.weatherapp.ui.utils.Constants
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.observeOn
import kotlinx.coroutines.launch

// TODO: Should be DI component
@ExperimentalAnimationApi
class WeatherActivity : ComponentActivity() {
    // TODO : A good practice would be to take all of UI components (@composable) and put it in a separate file from activity.

    // TODO: Should be injected
    private val viewModel: WeatherActivityViewModel = WeatherActivityViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.fetchData()
        setContent {
            BuildUi()
        }

    }

    private fun errorHandling() {
        Toast.makeText(this@WeatherActivity, R.string.report_error, Toast.LENGTH_LONG)
            .show()
    }

    @Composable
    fun BuildUi() {
        val timerOver by viewModel.isTimerOver.collectAsState()
        val progressMessage by viewModel.progressMessage.collectAsState()


        WeatherAppTheme {
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
                    if (timerOver) {
                        Column(
                            modifier = Modifier.fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            BuildWeatherReportTable()
                        }
                    }
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Bottom,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            progressMessage,
                            modifier = Modifier.fillMaxWidth(.5f),
                            textAlign = TextAlign.Center,
                            fontStyle = MaterialTheme.typography.button.fontStyle
                        )
                        if (!timerOver) {
                            BuildProgressBar()
                        } else {
                            Button(
                                modifier = Modifier
                                    .fillMaxWidth(.7f)
                                    .padding(Constants.PADDING.dp),
                                onClick = { viewModel.fetchData() }) {
                                Text(getString(R.string.report_restart_button))
                            }

                        }
                    }
                }
            }
        }
    }


    @Composable
    fun BuildProgressBar() {
        val progressBarValue by viewModel.progressBarValue.collectAsState()
        val progressBarText by viewModel.progressBarText.collectAsState()
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center, content = {
                // TODO : This should be screen-relative and not with DP
                Text(progressBarText, Modifier.padding(Constants.PADDING.dp))
                ProgressBar(value = progressBarValue)

            })

    }

    @Composable
    fun BuildWeatherReportTable() {
        val weatherReport by viewModel.weatherReportList.collectAsState()

        // TODO : I think a separate error handling should be better
        val errorAppears by viewModel.errorAppears.collectAsState()
        if (errorAppears) {
            errorHandling()
        }

        return LazyColumn(
            Modifier
                .fillMaxSize()
                .padding(15.dp)
        ) {
            item {
                Row(Modifier.background(MaterialTheme.colors.secondary)) {
                    TableCell(
                        text = getString(R.string.city_name),
                        weight = Constants.CELL_WIDTH,
                        textColor = Color.White
                    )
                    TableCell(
                        text = getString(R.string.city_temperature),
                        weight = Constants.CELL_WIDTH,
                        textColor = Color.White
                    )
                    TableCell(
                        text = getString(R.string.city_clouds),
                        weight = Constants.CELL_WIDTH,
                        textColor = Color.White
                    )
                }
            }

            items(weatherReport.mapIndexed { index, item ->
                index to item
            }) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TableCell(text = it.second.name, weight = Constants.CELL_WIDTH)
                    TableCell(
                        text = it.second.main.temp.toString() + Constants.CELCIUS,
                        weight = Constants.CELL_WIDTH
                    )
                    // According to API doc, It is possible to meet more than one weather condition for a requested location. The first weather condition in API respond is primary
                    TableCell(
                        text = it.second.weather.first().description,
                        weight = Constants.CELL_WIDTH
                    )
                }
            }

        }
    }
}
