package com.klim.smth.data.repository.symbol.data_source

import com.klim.smth.data.repository.symbol.data_source.dto.SearchStockSymbolDTO
import com.klim.smth.data.repository.symbol.data_source.dto.SymbolDetailsDTO

interface SymbolDataSourceI {

    suspend fun search(query: String): List<SearchStockSymbolDTO>

    suspend fun getDetails(ticker: String): SymbolDetailsDTO?
}