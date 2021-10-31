package com.klim.network_api

import com.klim.network_api.apis.HistoryApi
import com.klim.network_api.apis.SearchStockSymbolApi
import com.klim.network_api.apis.StockSymbolApi

interface ApiProvider {

    fun getHistoryApi(): HistoryApi

    fun getSearchStockSymbolApi(): SearchStockSymbolApi

    fun getStockSymbolApi(): StockSymbolApi
}