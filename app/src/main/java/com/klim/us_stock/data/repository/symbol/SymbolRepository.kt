package com.klim.us_stock.data.repository.symbol

import com.klim.us_stock.data.cache.Cache
//import com.klim.us_stock.data.cache.get
import com.klim.us_stock.data.repository.symbol.data_source.SymbolDataSourceI
import com.klim.us_stock.domain.entity.*
import com.klim.us_stock.domain.repository.SymbolRepositoryI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SymbolRepository
@Inject
constructor(
    private val remoteDataSource: SymbolDataSourceI,
    private val cacheDetails: Cache<String, SymbolDetailsEntity>,
) : SymbolRepositoryI {

    override suspend fun search(query: String): List<SearchResultEntity> {
        return remoteDataSource.search(query).map {
            it.map()
        }
    }

    //TODO now cache works every time because limit of free API
    override suspend fun getDetails(symbol: String): SymbolDetailsEntity? {
        var detailsEntity: SymbolDetailsEntity? = null
        if (!cacheDetails.isEmpty()) {
            detailsEntity = cacheDetails[symbol]
        }
        if (detailsEntity == null) {
            remoteDataSource.getDetails(symbol)?.let { details ->
                detailsEntity = details.map()
                cacheDetails[symbol] = detailsEntity
            }
        }
        return detailsEntity
    }

}