package com.klim.us_stock.data.repository.symbol.data_source

interface StockSymbolDataSourceI {
    suspend fun search(query: String): List<SearchStockSymbolDTO>
}