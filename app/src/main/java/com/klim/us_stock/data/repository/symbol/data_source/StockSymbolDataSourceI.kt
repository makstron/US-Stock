package com.klim.us_stock.data.repository.symbol.data_source

import com.klim.us_stock.data.repository.symbol.data_source.dto.SearchStockSymbolDTO
import com.klim.us_stock.data.repository.symbol.data_source.dto.SymbolDetailsDTO

interface StockSymbolDataSourceI {

    suspend fun search(query: String): List<SearchStockSymbolDTO>

    suspend fun getDetails(ticker: String): SymbolDetailsDTO?
}