package com.klim.us_stock.domain.repository

import com.klim.us_stock.domain.entity.SymbolPriceEntity

interface StockRepositoryI {

    suspend fun getLastPrice(symbol: String): SymbolPriceEntity?

    suspend fun getPreviousPrice(symbol: String): SymbolPriceEntity?
}