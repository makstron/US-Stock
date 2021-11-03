package com.klim.search_usecase

import com.klim.sr.SymbolRepositoryI
import javax.inject.Inject

class SearchUseCase
@Inject
constructor(val repository: SymbolRepositoryI) {

    suspend fun search(params: Params): List<com.klim.symbol_details_usecase_api.entity.SearchResultEntity> {
        return repository.search(params.request)
    }

    class Params(val request: String)

}