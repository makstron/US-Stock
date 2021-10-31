package com.klim.smth.data.repository.stock.data_source

import com.klim.smth.data.repository.stock.data_source.dto.SymbolPriceDTO
import com.klim.smth.data.retrofit.models.SymbolPriceResponse
import com.klim.smth.domain.entity.SymbolPriceEntity

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