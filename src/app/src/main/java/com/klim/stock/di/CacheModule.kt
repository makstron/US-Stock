package com.klim.stock.di

import com.klim.stock.cache.lru_cache.LRUCache
import com.klim.stock.symbol.api.entity.SymbolDetailsEntity
import com.klim.stock.symbol.api.entity.SymbolHistoryPriceEntity
import com.klim.stock.symbol.api.entity.SymbolPriceEntity
import com.klim.stock.Constants
import com.klim.stock.cache.Cache
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