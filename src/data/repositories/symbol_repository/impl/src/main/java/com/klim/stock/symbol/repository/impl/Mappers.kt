package com.klim.stock.symbol.repository.impl

import com.klim.stock.network.models.details.SymbolDetailsResult
import com.klim.stock.network.models.details.SymbolDetailsSummaryResponse
import com.klim.stock.network.models.details.SymbolDetailsSummaryResult
import com.klim.stock.network.models.search.SearchDocument
import com.klim.stock.symbol.api.entity.RelatedStockEntity
import com.klim.stock.symbol.api.entity.SymbolDetailsEntity
import com.klim.stock.symbol.api.entity.TagEntity
import com.klim.stock.searchusecase.api.entity.SearchResultEntity
import com.klim.stock.symbol.repository.impl.data_source.dto.RelatedStockDTO
import com.klim.stock.symbol.repository.impl.data_source.dto.SearchStockSymbolDTO
import com.klim.stock.symbol.repository.impl.data_source.dto.SymbolDetailsDTO
import com.klim.stock.symbol.repository.impl.data_source.dto.TagDTO

//search

fun SearchStockSymbolDTO.map(): SearchResultEntity {
    return SearchResultEntity(
        ticker = this.symbol,
        name = this.name,
    )
}

fun SearchDocument.map(): SearchStockSymbolDTO {
    return SearchStockSymbolDTO(
        symbol = this.symbol,
        name = this.name ?: this.symbol,
    )
}

//details

fun map(details: SymbolDetailsResult, detailsSummary: SymbolDetailsSummaryResult) =
    SymbolDetailsDTO(
        symbol = details.symbol,
        name = details.shortName,
        sector = detailsSummary.sector,
        industry = detailsSummary.industry,
        ceo = detailsSummary.ceo,
        employees = detailsSummary.employees,
        address = detailsSummary.hq_address,
        phone = detailsSummary.phone,
        description = detailsSummary.description,
        tags = mapTags(detailsSummary.tags),
        relatedStocks = mapRelatedStocks(detailsSummary.similar), //TODO: now
        currentPrice = details.currentPrice,
        marketChange = details.marketChange,
        marketChangePercent = details.marketChangePercent,
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
        currentPrice = currentPrice,
        marketChange = marketChange,
        marketChangePercent = marketChangePercent,
    )