package com.example.pvcompose

import androidx.compose.ui.graphics.Color


// Colors
val orange = Color(0xFFFFA500)
val lightGreen = Color(0xFF4CAF50)
val darkYellow = Color(0xFFFFE135)
val darkGreen = Color(0xFF009900)
val darkRed = Color(0xFF800000)

val colorList = listOf<Color>(
    Color.Red,
    orange,
    lightGreen,
    darkYellow,
    darkRed,
    darkGreen
)

//BarChart Data -----REPORT SCREEN -------------------------------------------------------------
data class BarData(
    val label: String,
    val barHeights: List<Float>,
    val colors: List<Color>,
) {
    val totalHeight: Float = barHeights.sum()
}

val barChartdata = listOf(
    BarData("Smart Fridge", listOf<Float>(10f, 30f, 10f, 0f, 5f, 15f), colorList),
    BarData("SmartPhone", listOf<Float>(20f, 30f, 10f,40f,80f,90f), colorList),
    BarData("Label1", listOf<Float>(20f, 50f, 0f, 13f, 30f,7f), colorList),
    BarData("Label2", listOf<Float>(60f, 10f, 10f, 0f, 0f, 15f), colorList),
    BarData("Label3", listOf<Float>(30f, 30f, 10f,50f, 30f,20f), colorList)
)