package com.klim.us_stock.data.repository.stock

import com.klim.us_stock.data.cache.Cache
import com.klim.us_stock.data.repository.stock.data_source.StockDataSourceI
import com.klim.us_stock.data.repository.stock.data_source.map
import com.klim.us_stock.domain.entity.SymbolPriceEntity
import com.klim.us_stock.domain.repository.StockRepositoryI
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
                cache.put(keyCache, symbolPriceEntity)
            }
        }
        return symbolPriceEntity
    }

}