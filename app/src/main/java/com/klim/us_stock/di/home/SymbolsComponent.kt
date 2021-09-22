package com.klim.us_stock.di.home

import com.klim.us_stock.di.main_activity.MainActivityModule
import dagger.Subcomponent
import com.klim.us_stock.ui.windows.home.SymbolsFragment

@SymbolsScope
@Subcomponent(modules = [MainActivityModule::class])
interface SymbolsComponent{

    fun inject(fragment: SymbolsFragment)

}