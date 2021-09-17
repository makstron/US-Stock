package com.klim.us_stock.data.repository.stock.data_source

import com.klim.us_stock.data.repository.stock.data_source.dto.SymbolPriceDTO
import com.klim.us_stock.data.retrofit.models.SymbolPriceResponse
import com.klim.us_stock.domain.entity.SymbolPriceEntity

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