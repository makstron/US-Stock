package com.klim.smth.domain.repository

import com.klim.smth.domain.entity.SearchResultEntity
import com.klim.smth.domain.entity.SymbolDetailsEntity

interface SymbolRepositoryI {

    suspend fun search(query: String): List<SearchResultEntity>

    suspend fun getDetails(symbol: String): SymbolDetailsEntity?
}