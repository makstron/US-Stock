package com.klim.stock.searchusecase.api.di

import com.klim.stock.dicore.Dependency
import com.klim.stock.searchusecase.api.SearchUseCase

interface SearchUseCaseProvider : Dependency {
    val symbolDetailsUseCase: SearchUseCase
}