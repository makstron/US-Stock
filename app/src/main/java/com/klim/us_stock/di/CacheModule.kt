package com.klim.us_stock.di

import com.klim.us_stock.Constants
import com.klim.us_stock.data.cache.Cache
import com.klim.us_stock.data.cache.lru_cache.LRUCache
import com.klim.us_stock.domain.entity.SymbolDetailsEntity
import com.klim.us_stock.domain.entity.SymbolHistoryPriceEntity
import com.klim.us_stock.domain.entity.SymbolPriceEntity
import dagger.Module
import dagger.Provides

@Module
class CacheModule {

    @Provides
    fun getCache_StockRepository_SymbolPriceEntity(): Cache<String, SymbolPriceEntity> {
        return LRUCache(Constants.Cache_StockRepository_SymbolPriceEntity_Size)
    }

    @Provides
    fun getCache_HistoryRepository_SymbolHistoryPriceEntity(): Cache<String, List<SymbolHistoryPriceEntity>> {
        return LRUCache(Constants.Cache_HistoryRepository_SymbolHistoryPriceEntity_Size)
    }

    @Provides
    fun getCache_StockRepository__SymbolDetailsEntity(): Cache<String, SymbolDetailsEntity> {
        return LRUCache(Constants.Cache_SymbolRepository_SymbolDetailsEntity_Size)
    }


}