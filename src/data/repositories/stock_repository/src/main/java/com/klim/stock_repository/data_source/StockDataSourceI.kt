package com.klim.stock_repository.data_source

import com.klim.stock_repository.data_source.dto.SymbolPriceDTO

interface StockDataSourceI {

    suspend fun getPrice(symbol: String, date: String): SymbolPriceDTO?
}