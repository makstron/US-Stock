package com.klim.stock.di

import com.klim.constants.di.ConstantsNetworkModule
import com.klim.stock.App
import com.klim.stock.analytics.di.AnalyticsBindModule
import com.klim.stock.database.di.DatabaseModule
import com.klim.stock.dependencyinjection.ApplicationContextProvider
import com.klim.stock.favorited.repository.impl.di.FavoritedRepositoryModule
import com.klim.stock.favorited.usecase.impl.di.FavoritedUseCaseModule
import com.klim.stock.chart.repository.impl.di.ChartRepositoryModule
import com.klim.stock.chart.usecase.impl.di.ChartUseCaseModule
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

        //Repositories
        SymbolRepositoryModule::class,
        ChartRepositoryModule::class,
        StockRepositoryModule::class,
        FavoritedRepositoryModule::class,

        //Analitycs
        AnalyticsBindModule::class,

        //Utils
        GeocoderModule::class,
        PhoneNumberUtilsModule::class,
        CoroutineModule::class,

        //Storages
        StorageKeysModule::class,
        CacheModule::class,
        DatabaseModule::class,

        //navigation
        NavigationModule::class,
        CreateNavigationModule::class,

        //Use Cases
        SymbolDetailsUseCaseModule::class,
        ChartUseCaseModule::class,
        SearchUseCaseModule::class,
        FavoritedUseCaseModule::class,
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