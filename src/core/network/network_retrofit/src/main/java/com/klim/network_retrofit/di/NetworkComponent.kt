package com.klim.network_retrofit.di

import com.klim.network_api.ApiProvider
import com.klim.network_api.apis.HistoryApi
import com.klim.network_api.apis.SearchStockSymbolApi
import com.klim.network_api.apis.StockSymbolApi
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
//    dependencies = [ApiProvider::class],
    modules = [RetrofitModule::class]
)
interface NetworkComponent : ApiProvider {

    override fun getHistoryApi(): HistoryApi

    override fun getSearchStockSymbolApi(): SearchStockSymbolApi

    override fun getStockSymbolApi(): StockSymbolApi

}