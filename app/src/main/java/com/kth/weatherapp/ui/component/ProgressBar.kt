package com.kth.weatherapp.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ProgressBar(value: Float) {
    LinearProgressIndicator(
        progress = value,
        color = colors.secondary,
        backgroundColor = colors.primary
    )
}