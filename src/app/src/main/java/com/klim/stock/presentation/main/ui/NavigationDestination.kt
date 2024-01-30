package com.klim.stock.presentation.main.ui

import androidx.compose.runtime.Immutable

@Immutable
sealed class NavigationDestination {
    object Favourite : NavigationDestination()
    object Settings : NavigationDestination()
    object Info : NavigationDestination()
}