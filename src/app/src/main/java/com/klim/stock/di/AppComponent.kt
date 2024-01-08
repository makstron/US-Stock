package com.klim.stock.di

import com.klim.constants.di.ConstantsNetworkModule
import com.klim.stock.App
import com.klim.stock.analytics.di.AnalyticsBindModule
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.history.repository.impl.di.HistoryRepositoryModule
import com.klim.stock.history.usecase.impl.di.HistoryUseCaseModule
import com.klim.stock.navigation.di.CreateNavigationModule
import com.klim.stock.navigation.di.NavigationModule
import com.klim.stock.network.ApiProvider
import com.klim.stock.search.usecase.impl.di.SearchUseCaseModule
import com.klim.stock.stock.repository.di.StockRepositoryModule
import com.klim.stock.storage.impl.di.StorageKeysModule
import com.klim.stock.symbol.repository.impl.di.SymbolRepositoryModule
import com.klim.stock.symbol.usecase.impl.di.SymbolDetailsUseCaseModule
import com.klim.stock.utils.coroutines.di.CoroutineModule
import com.klim.stock.utils.geocoder.di.GeocoderModule
import com.klim.stock.utils.phonenumber.di.PhoneNumberUtilsModule
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

        SymbolRepositoryModule::class,
        HistoryRepositoryModule::class,
        StockRepositoryModule::class,

        //Analitycs
        AnalyticsBindModule::class,

        //Utils
        GeocoderModule::class,
        PhoneNumberUtilsModule::class,
        CoroutineModule::class,

        //Storages
        StorageKeysModule::class,
        CacheModule::class,

        //navigation
        NavigationModule::class,
        CreateNavigationModule::class,

        //Use Cases
        SymbolDetailsUseCaseModule::class,
        HistoryUseCaseModule::class,
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