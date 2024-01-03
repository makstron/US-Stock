package com.klim.stock.di

import com.klim.constants.di.ConstantsNetworkModule
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.history.repository.di.HistoryRepositoryModule
import com.klim.stock.network.ApiProvider
import com.klim.stock.symbol.repository.impl.di.SymbolRepositoryModule
import com.klim.stock.analytics.di.AnalyticsBindModule
import com.klim.stock.search.usecase.impl.di.SearchUseCaseModule
import com.klim.stock.symbol.usecase.impl.di.SymbolDetailsUseCaseModule
import com.klim.stock.utils.geocoder.di.GeocoderModule
import com.klim.stock.utils.phonenumber.di.PhoneNumberUtilsModule
import com.klim.stock.stock.repository.di.StockRepositoryModule
import com.klim.stock.App
import com.klim.stock.di.view_model.ViewModelsBindModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [
        ApiProvider::class,
    ],
    modules = [
        AppModule::class,
        ConstantsNetworkModule::class,
        AppBindsModule::class,

        ViewModelsBindModule::class, //TODO: now remove from here

        SymbolRepositoryModule::class,
        HistoryRepositoryModule::class,
        StockRepositoryModule::class,

        CacheModule::class,

        //Analitycs
        AnalyticsBindModule::class,

        //Utils
        GeocoderModule::class,
        PhoneNumberUtilsModule::class,

        //Use Cases
        SymbolDetailsUseCaseModule::class,
        SearchUseCaseModule::class,
    ],
)
interface AppComponent : ApplicationContextProvider { //todo modules is it need here ApplicationContextProvider

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun app(app: App): Builder

        fun apiProvider(apiProvider: ApiProvider): Builder

        fun build(): AppComponent

    }

    fun inject(app: App)

}