package com.kth.weatherapp.model.data

class City(
    val id: Int,
    val name: String,
    val cityKey: String,
    // Wish it was a constant true...
    val isInBrittany: Boolean = false,
)
