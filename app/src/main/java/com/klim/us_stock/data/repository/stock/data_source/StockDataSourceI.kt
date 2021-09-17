package com.klim.us_stock.data.repository.stock.data_source

import com.klim.us_stock.data.repository.stock.data_source.dto.SymbolPriceDTO

interface StockDataSourceI {

    suspend fun getPrice(symbol: String, date: String): SymbolPriceDTO?
}