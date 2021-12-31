package com.kth.weatherapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.kth.weatherapp.ui.utils.Constants

@Composable
fun RowScope.TableCell(
    text: String,
    weight: Float,
    textColor: Color = Color.Black,
) {
    Text(
        text = text,
        color = textColor,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        modifier = Modifier
            .weight(weight)
            .fillMaxHeight(Constants.CELL_HEIGHT_FRACTION)
            .fillMaxWidth(Constants.CELL_WIDTH_FRACTION)
            .padding(Constants.CELL_PADDING.dp)

    )
}
