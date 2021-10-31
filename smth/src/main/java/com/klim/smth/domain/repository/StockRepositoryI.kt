package com.klim.smth.domain.repository

import com.klim.smth.domain.entity.SymbolPriceEntity

interface StockRepositoryI {

    suspend fun getPriceForDate(symbol: String, date: String): SymbolPriceEntity?
}