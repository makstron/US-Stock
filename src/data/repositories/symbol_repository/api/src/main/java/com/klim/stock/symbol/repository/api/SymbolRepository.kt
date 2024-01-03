package com.klim.stock.symbol.repository.api

import com.klim.stock.symbol.api.entity.SymbolDetailsEntity
import com.klim.stock.searchusecase.api.entity.SearchResultEntity

interface SymbolRepository {

    suspend fun search(query: String): List<SearchResultEntity>

    suspend fun getDetails(symbol: String): SymbolDetailsEntity?
}