package com.klim.stock.network.retrofit.di

import com.klim.stock.network.ApiProvider
import com.klim.stock.network.api.ChartApi
import com.klim.stock.network.api.SearchApi
import com.klim.stock.network.api.StockSymbolApi
import com.klim.stock.network.api.SymbolDetailsApi
import com.klim.stock.storage.api.StorageKeyProvider
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    dependencies = [StorageKeyProvider::class],
    modules = [RetrofitApiModule::class]
)
interface NetworkComponent : ApiProvider {

    override fun getChartApi(): ChartApi

    override fun getSearchApi(): SearchApi

    override fun getStockSymbolApi(): StockSymbolApi

    override fun getSymbolDetailsApi(): SymbolDetailsApi

}