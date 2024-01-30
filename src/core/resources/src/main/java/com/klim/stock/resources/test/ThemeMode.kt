package com.klim.stock.resources.test

sealed class ThemeMode(val title: String) {
    object Light: ThemeMode(title = "Light")
    object Dark: ThemeMode(title = "Dark")
    object Space: ThemeMode(title = "Space")
}