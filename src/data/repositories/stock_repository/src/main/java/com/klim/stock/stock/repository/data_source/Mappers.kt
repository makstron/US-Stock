package com.klim.stock.stock.repository.data_source

import com.klim.stock.network.models.SymbolPriceResponse
import com.klim.stock.symbol.api.entity.SymbolPriceEntity
import com.klim.stock.stock.repository.data_source.dto.SymbolPriceDTO

fun SymbolPriceResponse.map() =
    SymbolPriceDTO(
        open = this.open,
        close = this.close,
    )

fun SymbolPriceDTO.map() =
    SymbolPriceEntity(
        open = this.open,
        close = this.close,
    )