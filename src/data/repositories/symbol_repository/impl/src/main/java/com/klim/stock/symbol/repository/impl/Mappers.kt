package com.klim.stock.symbol.repository.impl

import com.klim.stock.network.models.details.SymbolDetailsResult
import com.klim.stock.network.models.details.SymbolDetailsSummaryResult
import com.klim.stock.network.models.details.SymbolOfficer
import com.klim.stock.network.models.search.SearchDocument
import com.klim.stock.searchusecase.api.entity.SearchResultEntity
import com.klim.stock.symbol.api.entity.OfficerEntity
import com.klim.stock.symbol.api.entity.RecommendedSymbolRepositoryEntity
import com.klim.stock.symbol.api.entity.SymbolDetailsRepositoryEntity
import com.klim.stock.symbol.repository.impl.data_source.dto.OfficerDTO
import com.klim.stock.symbol.repository.impl.data_source.dto.RecommendedSymbolDTO
import com.klim.stock.symbol.repository.impl.data_source.dto.SearchStockSymbolDTO
import com.klim.stock.symbol.repository.impl.data_source.dto.SymbolDetailsDTO

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

fun map(detailsSummary: SymbolDetailsSummaryResult, details: SymbolDetailsResult, recommendation: List<String>) =
    SymbolDetailsDTO(
        symbol = details.symbol,
        name = details.shortName,
        sector = detailsSummary.sector,
        industry = detailsSummary.industry,
        employees = detailsSummary.employees,
        address = detailsSummary.hqAddress,
        phone = detailsSummary.phone,
        description = detailsSummary.description,
        officers = mapOfficers(detailsSummary.officers),
        recommendedSymbols = mapRecommendedStocks(recommendation),
        currentPrice = details.currentPrice,
        marketChange = details.marketChange,
        marketChangePercent = details.marketChangePercent,
    )

fun mapOfficers(tags: List<SymbolOfficer>): List<OfficerDTO> {
    return tags.map {
        OfficerDTO(it.name, it.title)
    }
}

fun mapRecommendedStocks(stocks: List<String>): List<RecommendedSymbolDTO> {
    return stocks.map {
        RecommendedSymbolDTO(it)
    }
}

fun SymbolDetailsDTO.map() =
    SymbolDetailsRepositoryEntity(
        symbol = this.symbol,
        name = this.name,
        sector = this.sector,
        industry = this.industry,
        employees = this.employees,
        address = this.address,
        phone = this.phone,
        description = this.description,
        officers = this.officers.map { OfficerEntity(it.name, it.title) },
        recommendedSymbols = this.recommendedSymbols.map { RecommendedSymbolRepositoryEntity(it.symbol) },
        currentPrice = currentPrice,
        marketChange = marketChange,
        marketChangePercent = marketChangePercent,
    )