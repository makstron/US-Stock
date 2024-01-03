package com.klim.stock.symbol.api.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.symbol.api.SymbolDetailsUseCase

interface SymbolDetailsUseCaseProvider : Dependency {
    val symbolDetailsUseCase: SymbolDetailsUseCase
}