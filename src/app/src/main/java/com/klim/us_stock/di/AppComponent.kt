package com.klim.us_stock.di

import com.klim.analytics.di.FirebaseModule
import com.klim.di.ApplicationContextProvider
import com.klim.di.view_model.ViewModelsBindModule
import com.klim.smth.di.DataSourcesModule
import com.klim.smth.di.RepositoryBindModule
import com.klim.smth.di.RepositoryModule
import com.klim.smth.di.RetrofitModule
import com.klim.us_stock.App
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AppBindsModule::class,
        ViewModelsBindModule::class,
        RepositoryModule::class,
        RepositoryBindModule::class,
        DataSourcesModule::class,
        RetrofitModule::class,
        UtilsModule::class,
        FirebaseModule::class,
        CacheModule::class,
    ],
)
interface AppComponent : AndroidInjector<App>, ApplicationContextProvider { //todo modules is it need here ApplicationContextProvider

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: App): Builder

        fun build(): AppComponent

    }

    override fun inject(app: App)

//    fun getMainActivityComponent(): MainActivityComponent
//
//    fun getSymbolComponent(): SymbolsComponent
//
//    fun getSettingsComponent(): SettingsComponent
//
//    fun getInfoComponent(): InfoComponent
//
////    fun getSymbolDetailsComponent(): SymbolDetailsComponent
//
//    fun getSearchComponent(): SearchComponent

}