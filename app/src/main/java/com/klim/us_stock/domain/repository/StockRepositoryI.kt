package com.klim.us_stock.domain.repository

import com.klim.us_stock.domain.entity.SymbolPriceEntity

interface StockRepositoryI {

    suspend fun getPriceForDate(symbol: String, date: String): SymbolPriceEntity?
}