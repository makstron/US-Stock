package com.klim.us_stock.data.repository.symbol

import com.klim.us_stock.data.repository.symbol.data_source.SymbolDataSourceI
import com.klim.us_stock.domain.entity.RelatedStockEntity
import com.klim.us_stock.domain.entity.SearchResultEntity
import com.klim.us_stock.domain.entity.SymbolDetailsEntity
import com.klim.us_stock.domain.entity.TagEntity
import com.klim.us_stock.domain.repository.SymbolRepositoryI

class SymbolRepository(private val remoteDataSource: SymbolDataSourceI) : SymbolRepositoryI {

    override suspend fun search(query: String): List<SearchResultEntity> {
        return remoteDataSource.search(query).map {
            it.map()
        }
    }

    override suspend fun getDetails(symbol: String): SymbolDetailsEntity? {
        val details = remoteDataSource.getDetails(symbol)
        details?.let {
            return details.map()
        } ?: run {
            return null
        }
    }

}