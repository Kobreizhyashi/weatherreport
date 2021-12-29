package com.kth.weatherapp.ui.features.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.kth.weatherapp.R
import com.kth.weatherapp.ui.component.ProgressBar
import com.kth.weatherapp.ui.features.weather.WeatherActivity
import com.kth.weatherapp.ui.theme.WeatherAppTheme

// TODO: Should be DI component
@ExperimentalAnimationApi
class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                // TODO : The "cleaner" way should be to separate does Composable components from activity

                Surface(color = MaterialTheme.colors.background) {
                    Scaffold {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                // Not a magic number, this is just
                                modifier = Modifier.fillMaxWidth(0.5f),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Row(modifier = Modifier.padding(Dp(15f))) {
                                    Text(
                                        textAlign = TextAlign.Center,
                                        text = getString(R.string.home_text)
                                    )
                                }

                                Button(
                                    colors = ButtonDefaults.buttonColors(contentColor = MaterialTheme.colors.background,  backgroundColor = MaterialTheme.colors.secondary, ),
                                    onClick = { runWeatherReport() }) {
                                    Text(getString(R.string.home_button))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun runWeatherReport() {
        val intent = Intent(this, WeatherActivity::class.java)
        startActivity(intent)
    }
}
