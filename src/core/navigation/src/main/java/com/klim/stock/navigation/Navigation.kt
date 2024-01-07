package com.klim.stock.navigation

import androidx.fragment.app.Fragment

interface Navigation {

    fun getDetailsScreen(symbol: String): Fragment

}