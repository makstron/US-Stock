package com.klim.stock_repository.data_source

import com.klim.coreUi.data.retrofit.models.SymbolPriceResponse
import com.klim.coreUi.domain.entity.SymbolPriceEntity
import com.klim.stock_repository.data_source.dto.SymbolPriceDTO

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