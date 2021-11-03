package com.klim.sr

import com.klim.coreUi.data.retrofit.models.SearchResultItem
import com.klim.coreUi.data.retrofit.models.SymbolDetailsResponse
import com.klim.coreUi.domain.entity.RelatedStockEntity
import com.klim.coreUi.domain.entity.SymbolDetailsEntity
import com.klim.coreUi.domain.entity.TagEntity
import com.klim.sr.data_source.dto.RelatedStockDTO
import com.klim.sr.data_source.dto.SearchStockSymbolDTO
import com.klim.sr.data_source.dto.SymbolDetailsDTO
import com.klim.sr.data_source.dto.TagDTO
import com.klim.symbol_details_usecase_api.entity.SearchResultEntity

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

fun mapTags(tags: List<String>) : List<TagDTO> {
    return tags.map {
        TagDTO(it)
    }
}

fun mapRelatedStocks(stocks: List<String>) : List<RelatedStockDTO> {
    return stocks.map {
        RelatedStockDTO(it)
    }
}

fun SymbolDetailsDTO.map() =
    SymbolDetailsEntity(
        symbol = this.symbol,
        name = this.name,
        sector = this.sector,
        industry = this.industry,
        ceo = this.ceo,
        employees = this.employees,
        address = this.address,
        phone = this.phone,
        description = this.description,
        tags = this.tags.map { TagEntity(it.tag) },
        relatedStocks = this.relatedStocks.map { RelatedStockEntity(it.symbol) },
    )