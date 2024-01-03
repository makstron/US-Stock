package com.klim.stock.stock.repository

import com.klim.stock.cache.Cache
import com.klim.stock.symbol.api.entity.SymbolPriceEntity
import com.klim.stock.stock.repository.data_source.StockDataSourceI
import com.klim.stock.stock.repository.data_source.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepository
@Inject
constructor(
    private val remoteDataSrc: StockDataSourceI,
    private val cache: Cache<String, SymbolPriceEntity>,
) : StockRepositoryI {

    //TODO now cache works every time because limit of free API
    override suspend fun getPriceForDate(symbol: String, date: String): SymbolPriceEntity? {
        val keyCache = "${symbol}_${date}"
        var symbolPriceEntity: SymbolPriceEntity? = null
        if (!cache.isEmpty()) {
            symbolPriceEntity = cache.get(keyCache)
        }
        if (symbolPriceEntity == null) {
            remoteDataSrc.getPrice(symbol, date)?.let {
                symbolPriceEntity = it.map()
                cache.set(keyCache, symbolPriceEntity)
            }
        }
        return symbolPriceEntity
    }

}