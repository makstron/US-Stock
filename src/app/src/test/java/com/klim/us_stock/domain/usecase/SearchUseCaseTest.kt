package com.klim.us_stock.domain.usecase

import com.google.common.truth.Truth.assertThat
import com.klim.stock.searchusecase.api.SearchUseCase
import com.klim.stock.searchusecase.api.entity.SearchResultEntity
import com.klim.stock.search.usecase.impl.SearchUseCaseImpl
import com.klim.stock.symbol.repository.api.SymbolRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class SearchUseCaseTest {

    private lateinit var repository: SymbolRepository
    private lateinit var useCase: SearchUseCase
    private val request = "Tes"
    private val requestParams = SearchUseCase.RequestParams(request)
    private val response = listOf(
        SearchResultEntity("TSLA", "Tesla Inc"),
    )

    @Before
    fun setUp() {
        repository = mockk<SymbolRepository>{
            coEvery { search(request) } returns response
        }
        useCase = SearchUseCaseImpl(repository)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun search() {
        runBlockingTest {
            val searchResults = useCase.search(requestParams)
            assertThat(searchResults).containsExactlyElementsIn(response)
        }
    }

}