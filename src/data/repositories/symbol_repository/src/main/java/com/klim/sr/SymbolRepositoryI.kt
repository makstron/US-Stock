package com.klim.sr

import com.klim.coreUi.domain.entity.SymbolDetailsEntity
import com.klim.symbol_details_usecase_api.entity.SearchResultEntity

interface SymbolRepositoryI {

    suspend fun search(query: String): List<SearchResultEntity>

    suspend fun getDetails(symbol: String): SymbolDetailsEntity?
}