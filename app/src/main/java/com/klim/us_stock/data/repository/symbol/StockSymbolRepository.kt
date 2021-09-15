package com.klim.us_stock.data.repository.symbol

import com.klim.us_stock.data.repository.symbol.data_source.remote.StockSymbolRemoteDataSource
import com.klim.us_stock.domain.entity.SearchResultEntity
import com.klim.us_stock.domain.repository.StockSymbolRepositoryI

class StockSymbolRepository(private val remoteDataSource: StockSymbolRemoteDataSource): StockSymbolRepositoryI {

    override suspend fun search(query: String): List<SearchResultEntity> {
        return remoteDataSource.search(query).map {
            it.map()
        }
    }
}