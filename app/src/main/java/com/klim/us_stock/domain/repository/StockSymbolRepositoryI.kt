package com.klim.us_stock.domain.repository

import com.klim.us_stock.domain.entity.SearchResultEntity
import com.klim.us_stock.domain.entity.SymbolDetailsEntity

interface StockSymbolRepositoryI {

    suspend fun search(query: String): List<SearchResultEntity>

    suspend fun getDetails(symbol: String): SymbolDetailsEntity?
}