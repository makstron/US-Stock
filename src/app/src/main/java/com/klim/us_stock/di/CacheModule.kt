package com.klim.us_stock.di

import com.klim.cache.lru_cache.LRUCache
import com.klim.coreUi.domain.entity.SymbolDetailsEntity
import com.klim.coreUi.domain.entity.SymbolHistoryPriceEntity
import com.klim.coreUi.domain.entity.SymbolPriceEntity
import com.klim.us_stock.Constants
import dagger.Module
import dagger.Provides

@Module
class CacheModule {

    @Provides
    fun getCache_StockRepository_SymbolPriceEntity(): com.klim.cache.Cache<String, SymbolPriceEntity> {
        return LRUCache(Constants.Cache_StockRepository_SymbolPriceEntity_Size)
    }

    @Provides
    fun getCache_HistoryRepository_SymbolHistoryPriceEntity(): com.klim.cache.Cache<String, List<SymbolHistoryPriceEntity>> {
        return LRUCache(Constants.Cache_HistoryRepository_SymbolHistoryPriceEntity_Size)
    }

    @Provides
    fun getCache_StockRepository__SymbolDetailsEntity(): com.klim.cache.Cache<String, SymbolDetailsEntity> {
        return LRUCache(Constants.Cache_SymbolRepository_SymbolDetailsEntity_Size)
    }


}