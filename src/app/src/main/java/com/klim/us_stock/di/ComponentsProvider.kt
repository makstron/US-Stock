package com.klim.us_stock.di

//import com.klim.di.AppComponent
import com.klim.us_stock.App
import com.klim.us_stock.di.home.SymbolsComponent
import com.klim.us_stock.di.info.InfoComponent
import com.klim.us_stock.di.main_activity.MainActivityComponent
import com.klim.us_stock.di.search.SearchComponent
import com.klim.us_stock.di.settings.SettingsComponent

class ComponentsProvider(val app: App) {

    val appComponent: AppComponent
        get

    init {
        appComponent = DaggerAppComponent
            .builder()
            .app(app)
            .build()
    }

//    /////// MainActivityComponent
//
//    private var mainActivityComponent: MainActivityComponent? = null
//
//    fun getMainActivityComponent(): MainActivityComponent {
//        if (mainActivityComponent == null) mainActivityComponent = appComponent.getMainActivityComponent()
//        return mainActivityComponent!!
//    }
//
//    fun destroyMainActivityComponent() {
//        mainActivityComponent = null
//    }
//
//    /////// SymbolsComponent
//
//    private var symbolsComponent: SymbolsComponent? = null
//
//    fun getSymbolComponent(): SymbolsComponent {
//        if (symbolsComponent == null) symbolsComponent = appComponent.getSymbolComponent()
//        return symbolsComponent!!
//    }
//
//    fun destroySymbolComponent() {
//        symbolsComponent = null
//    }
//
//    /////// SettingsComponent
//
//    private var settingsComponent: SettingsComponent? = null
//
//    fun getSettingsComponent(): SettingsComponent {
//        if (settingsComponent == null) settingsComponent = appComponent.getSettingsComponent()
//        return settingsComponent!!
//    }
//
//    fun destroySettingsComponent() {
//        settingsComponent = null
//    }
//
//    /////// InfoComponent
//
//    private var infoComponent: InfoComponent? = null
//
//    fun getInfoComponent(): InfoComponent {
//        if (infoComponent == null) infoComponent = appComponent.getInfoComponent()
//        return infoComponent!!
//    }
//
//    fun destroyInfoComponent() {
//        infoComponent = null
//    }

    /////// SymbolDetailsComponent

//    private var symbolDetailsComponent: SymbolDetailsComponent? = null
//
//    fun getSymbolDetailsComponent(): SymbolDetailsComponent {
//        if (symbolDetailsComponent == null) symbolDetailsComponent = appComponent.getSymbolDetailsComponent()
//        return symbolDetailsComponent!!
//    }
//
//    fun destroySymbolDetailsComponent() {
//        symbolDetailsComponent = null
//    }

//    /////// SearchComponent
//
//    private var searchComponentComponent: SearchComponent? = null
//
//    fun getSearchComponent(): SearchComponent {
//        if (searchComponentComponent == null) searchComponentComponent = appComponent.getSearchComponent()
//        return searchComponentComponent!!
//    }
//
//    fun destroySearchComponent() {
//        searchComponentComponent = null
//    }

    ///////


}