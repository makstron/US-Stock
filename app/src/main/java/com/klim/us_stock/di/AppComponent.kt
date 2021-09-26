package com.klim.us_stock.di

import com.klim.us_stock.App
import com.klim.us_stock.di.home.SymbolsComponent
import com.klim.us_stock.di.info.InfoComponent
import com.klim.us_stock.di.main_activity.MainActivityComponent
import com.klim.us_stock.di.search.SearchComponent
import com.klim.us_stock.di.settings.SettingsComponent
import com.klim.us_stock.di.symbol_details.SymbolDetailsComponent
import com.klim.us_stock.di.view_model.ViewModelsBindModule
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        ViewModelsBindModule::class,
        RepositoryModule::class,
        DataSourcesModule::class,
        RetrofitModule::class,
        UtilsModule::class,
        FirebaseModule::class,
    ],
)
interface AppComponent : AndroidInjector<App> {

    override fun inject(app: App)

    fun getMainActivityComponent(): MainActivityComponent

    fun getSymbolComponent(): SymbolsComponent

    fun getSettingsComponent(): SettingsComponent

    fun getInfoComponent(): InfoComponent

    fun getSymbolDetailsComponent(): SymbolDetailsComponent

    fun getSearchComponent(): SearchComponent

}