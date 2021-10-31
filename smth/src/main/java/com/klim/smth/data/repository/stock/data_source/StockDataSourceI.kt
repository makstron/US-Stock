package com.klim.smth.data.repository.stock.data_source

import com.klim.smth.data.repository.stock.data_source.dto.SymbolPriceDTO

interface StockDataSourceI {

    suspend fun getPrice(symbol: String, date: String): SymbolPriceDTO?
}