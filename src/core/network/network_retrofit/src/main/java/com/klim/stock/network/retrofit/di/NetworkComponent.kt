package com.klim.stock.network.retrofit.di

import com.klim.stock.network.ApiProvider
import com.klim.stock.network.api.HistoryApi
import com.klim.stock.network.api.SearchStockSymbolApi
import com.klim.stock.network.api.StockSymbolApi
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