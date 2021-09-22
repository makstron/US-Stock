package com.klim.us_stock.domain.usecase

import com.klim.us_stock.domain.entity.SearchResultEntity
import com.klim.us_stock.domain.repository.SymbolRepositoryI
import javax.inject.Inject

class SearchUseCase
@Inject
constructor(val repository: SymbolRepositoryI) {

    suspend fun search(params: Params): List<SearchResultEntity> {
        return repository.search(params.request)
    }

    class Params(val request: String)

}