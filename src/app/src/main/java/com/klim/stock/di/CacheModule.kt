package com.klim.stock.di

import com.klim.stock.Constants
import com.klim.stock.cache.Cache
import com.klim.stock.cache.lru_cache.LRUCache
import com.klim.stock.chart.usecase.api.entity.SymbolChartEntity
import com.klim.stock.symbol.api.entity.SymbolDetailsRepositoryEntity
import com.klim.stock.symbol.api.entity.SymbolPriceEntity
import dagger.Module
import dagger.Provides

@Module
class CacheModule {

    @Provides
    fun getCache_StockRepository_SymbolPriceEntity(): Cache<String, SymbolPriceEntity> {
        return LRUCache(Constants.Cache_StockRepository_SymbolPriceEntity_Size)
    }

    @Provides
    fun getCache_ChartRepository_SymbolChartEntity(): Cache<String, List<SymbolChartEntity>> {
        return LRUCache(Constants.Cache_ChartRepository_SymbolChartEntity_Size)
    }

    @Provides
    fun getCache_StockRepository__SymbolDetailsEntity(): Cache<String, SymbolDetailsRepositoryEntity> {
        return LRUCache(Constants.Cache_SymbolRepository_SymbolDetailsEntity_Size)
    }


}