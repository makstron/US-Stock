package com.klim.stock.stock.repository

import com.klim.stock.symbol.api.entity.SymbolPriceEntity

interface StockRepositoryI {

    suspend fun getPriceForDate(symbol: String, date: String): SymbolPriceEntity?
}