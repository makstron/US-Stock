package com.klim.stock.favorited.ui.api

sealed class NavigationTarget {
    class SymbolDetails(val symbol: String): NavigationTarget()
}