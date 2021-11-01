package com.klim.us_stock.di

import com.klim.analytics.di.AnalyticsDependency
import com.klim.constants.di.ConstantsNetworkModule
import com.klim.dep_in.ApplicationContextProvider
import com.klim.network_api.ApiProvider
import com.klim.smth.di.DataSourcesModule
import com.klim.smth.di.RepositoryBindModule
import com.klim.smth.di.RepositoryModule
import com.klim.us_stock.App
import com.klim.us_stock.di.view_model.ViewModelsBindModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        ApiProvider::class,
        AnalyticsDependency::class,
    ],
    modules = [
        AppModule::class,
        ConstantsNetworkModule::class,
        AppBindsModule::class,
        ViewModelsBindModule::class,
        RepositoryModule::class,
        RepositoryBindModule::class,
        DataSourcesModule::class,
        UtilsModule::class,
        CacheModule::class,
    ],
)
interface AppComponent : AndroidInjector<App>, ApplicationContextProvider { //todo modules is it need here ApplicationContextProvider

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: App): Builder

        fun apiProvider(apiProvider: ApiProvider): Builder

        fun analyticsDependency(analyticsDependency: AnalyticsDependency): Builder

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