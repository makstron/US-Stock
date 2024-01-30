package com.klim.stock.presentation.main.ui

class ActionListeners(
    val menu: () -> Unit,
    val search: () -> Unit,
    val favorited: () -> Unit,
    val settings: () -> Unit,
    val info: () -> Unit,
    val exit: () -> Unit,
)