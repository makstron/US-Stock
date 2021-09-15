package com.klim.us_stock.data.repository.symbol

import com.klim.us_stock.data.repository.symbol.data_source.SearchStockSymbolDTO
import com.klim.us_stock.data.retrofit.models.SearchResultItem
import com.klim.us_stock.domain.entity.SearchResultEntity

fun SearchStockSymbolDTO.map(): SearchResultEntity {
    return SearchResultEntity(
        ticker = this.ticker,
        name = this.name,
    )
}

fun SearchResultItem.map(): SearchStockSymbolDTO {
    return SearchStockSymbolDTO(
        ticker = this.ticker,
        name = this.name,
    )
}