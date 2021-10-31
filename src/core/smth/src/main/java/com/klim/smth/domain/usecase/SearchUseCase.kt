package com.klim.smth.domain.usecase

import com.klim.smth.domain.entity.SearchResultEntity
import com.klim.smth.domain.repository.SymbolRepositoryI
import javax.inject.Inject

class SearchUseCase
@Inject
constructor(val repository: SymbolRepositoryI) {

    suspend fun search(params: Params): List<SearchResultEntity> {
        return repository.search(params.request)
    }

    class Params(val request: String)

}