package com.klim.stock.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.klim.stock.symbol.ui.presentation.SymbolDetailsFragment

class NavigationImpl: Navigation {

    override fun getDetailsScreen(symbol: String): Fragment {
        val args = Bundle().apply {
            putString(SymbolDetailsFragment.SYMBOL, symbol)
        }
        return SymbolDetailsFragment.newInstance(args)
    }

}