package com.klim.us_stock.domain.repository

import com.klim.us_stock.domain.entity.SearchResultEntity

interface StockSymbolRepositoryI {
    suspend fun search(query: String): List<SearchResultEntity>
}