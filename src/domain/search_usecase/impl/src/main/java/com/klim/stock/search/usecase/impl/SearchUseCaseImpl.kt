package com.klim.stock.search.usecase.impl

import com.klim.stock.searchusecase.api.SearchUseCase
import com.klim.stock.searchusecase.api.entity.SearchResultEntity
import com.klim.stock.symbol.repository.api.SymbolRepository
import javax.inject.Inject

class SearchUseCaseImpl
@Inject
constructor(private val repository: SymbolRepository): SearchUseCase {

    override suspend fun search(params: SearchUseCase.RequestParams): List<SearchResultEntity> {
        return repository.search(params.query)
    }

}