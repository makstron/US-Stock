package com.klim.us_stock.di.main_activity

import com.klim.us_stock.ui.MainActivity
import dagger.Subcomponent

@MainActivityScope
@Subcomponent(modules = [MainActivityModule::class])
interface MainActivityComponent{

    fun inject(activity: MainActivity)

}