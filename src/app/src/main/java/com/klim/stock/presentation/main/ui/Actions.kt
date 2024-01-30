package com.klim.stock.presentation.main.ui

sealed class Actions {
    class Navigation(val destination: NavigationDestination) : Actions()
    class MenuButton : Actions()
    class SearchButton : Actions()
    class ExitButton : Actions()
}