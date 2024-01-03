package com.klim.stock.symbol.repository.impl

import com.klim.stock.network.models.SymbolDetailsResponse
import com.klim.stock.symbol.api.entity.RelatedStockEntity
import com.klim.stock.symbol.api.entity.SymbolDetailsEntity
import com.klim.stock.symbol.api.entity.TagEntity
import com.klim.stock.network.models.SearchResultItem
import com.klim.stock.searchusecase.api.entity.SearchResultEntity
import com.klim.stock.symbol.repository.impl.data_source.dto.RelatedStockDTO
import com.klim.stock.symbol.repository.impl.data_source.dto.SearchStockSymbolDTO
import com.klim.stock.symbol.repository.impl.data_source.dto.SymbolDetailsDTO
import com.klim.stock.symbol.repository.impl.data_source.dto.TagDTO

//search

fun SearchStockSymbolDTO.map(): SearchResultEntity {
    return SearchResultEntity(
        ticker = this.ticker,
        name = this.name,
    )
}

fun SearchResultItem.map(): SearchStockSymbolDTO {
    println("@@@ " + this.name) //TODO: now remove it
    return SearchStockSymbolDTO(
        ticker = this.ticker,
        name = this.name ?: "", //TODO: now name can bu null
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

fun mapTags(tags: List<String>): List<TagDTO> {
    return tags.map {
        TagDTO(it)
    }
}

fun mapRelatedStocks(stocks: List<String>): List<RelatedStockDTO> {
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