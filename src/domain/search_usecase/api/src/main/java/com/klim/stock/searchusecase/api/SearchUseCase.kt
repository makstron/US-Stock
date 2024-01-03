package com.klim.stock.searchusecase.api

import com.klim.stock.searchusecase.api.entity.SearchResultEntity


interface SearchUseCase {

    class RequestParams(val query: String)

    suspend fun search(params: RequestParams): List<SearchResultEntity>

}