package com.klim.stock_repository

import com.klim.coreUi.domain.entity.SymbolPriceEntity

interface StockRepositoryI {

    suspend fun getPriceForDate(symbol: String, date: String): SymbolPriceEntity?
}