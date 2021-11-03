package com.klim.sr.data_source

import com.klim.sr.data_source.dto.SearchStockSymbolDTO
import com.klim.sr.data_source.dto.SymbolDetailsDTO

interface SymbolDataSourceI {

    suspend fun search(query: String): List<SearchStockSymbolDTO>

    suspend fun getDetails(ticker: String): SymbolDetailsDTO?
}