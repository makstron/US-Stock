package com.klim.us_stock.data.repository.symbol

import com.klim.us_stock.data.repository.symbol.data_source.dto.RelatedStockDTO
import com.klim.us_stock.data.repository.symbol.data_source.dto.SearchStockSymbolDTO
import com.klim.us_stock.data.repository.symbol.data_source.dto.SymbolDetailsDTO
import com.klim.us_stock.data.repository.symbol.data_source.dto.TagDTO
import com.klim.us_stock.data.retrofit.models.SearchResultItem
import com.klim.us_stock.data.retrofit.models.SymbolDetailsResponse
import com.klim.us_stock.domain.entity.SearchResultEntity

//search

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

//details

fun SymbolDetailsResponse.map() =
    SymbolDetailsDTO(
        symbol = this.symbol,
        name = this.name,
        sector = this.sector,
        industry = this.industry,
        ceo = this.ceo,
        employees = this.employees,
        address = this.hq_address,
        phone = this.phone,
        description = this.description,
        tags = mapTags(this.tags),
        relatedStocks = mapRelatedStocks(this.similar),
    )

fun SymbolDetailsResponse.mapTags(tags: List<String>) : List<TagDTO> {
    return tags.map {
        TagDTO(it)
    }
}

fun SymbolDetailsResponse.mapRelatedStocks(stocks: List<String>) : List<RelatedStockDTO> {
    return stocks.map {
        RelatedStockDTO(it)
    }
}