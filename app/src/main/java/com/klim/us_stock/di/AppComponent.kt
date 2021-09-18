package com.klim.us_stock.di

import com.klim.us_stock.App
import com.klim.us_stock.di.home.HomeComponent
import com.klim.us_stock.di.home.HomeModule
import com.klim.us_stock.di.info.InfoComponent
import com.klim.us_stock.di.info.InfoModule
import com.klim.us_stock.di.search.SearchComponent
import com.klim.us_stock.di.search.SearchModule
import com.klim.us_stock.di.settings.SettingsComponent
import com.klim.us_stock.di.settings.SettingsModule
import com.klim.us_stock.di.symbol_details.SymbolDetailsComponent
import com.klim.us_stock.di.symbol_details.SymbolDetailsModule
import com.klim.us_stock.di.view_model.ViewModelFactoryModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelFactoryModule::class,
        RepositoryModule::class,
        DataSourcesModule::class,
        RetrofitModule::class,
    ]
)
interface AppComponent {

    fun inject(app: App)

    fun getHomeComponent(module: HomeModule): HomeComponent

    fun getSettingsComponent(module: SettingsModule): SettingsComponent

    fun getInfoComponent(module: InfoModule): InfoComponent

    fun getSymbolDetailsComponent(module: SymbolDetailsModule): SymbolDetailsComponent

    fun getSearchComponent(module: SearchModule): SearchComponent

}