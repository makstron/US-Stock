package com.klim.stock.symbol.repository.impl

import com.klim.stock.cache.Cache
import com.klim.stock.searchusecase.api.entity.SearchResultEntity
import com.klim.stock.symbol.api.entity.SymbolDetailsRepositoryEntity
import com.klim.stock.symbol.repository.api.SymbolRepository
import com.klim.stock.symbol.repository.impl.data_source.SymbolDataSourceI
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SymbolRepositoryImpl
@Inject
constructor(
    private val remoteDataSource: SymbolDataSourceI,
    private val cacheDetails: Cache<String, SymbolDetailsRepositoryEntity>,
) : SymbolRepository {

    override suspend fun search(query: String): List<SearchResultEntity> {
        return remoteDataSource.search(query).map {
            it.map()
        }
    }

    //TODO now cache works every time because limit of free API
    override suspend fun getDetails(symbol: String): SymbolDetailsRepositoryEntity? {
        var detailsEntity: SymbolDetailsRepositoryEntity? = null
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